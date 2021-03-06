package audiow.podcast.data.repository.podcast

import io.reactivex.Completable
import io.reactivex.Single
import audiow.core.data.repository.Repository
import audiow.core.util.reactive.scheduler.SchedulersProvider
import audiow.podcast.data.model.Genre
import audiow.podcast.data.model.Podcast
import audiow.podcast.data.storage.database.dao.PodcastDAO
import audiow.podcast.service.itunes.ItunesService
import io.reactivex.Maybe
import toDomain
import toEntity
import toWrapperEntity
import javax.inject.Inject

internal class PodcastRepository @Inject constructor(
    private val dao: PodcastDAO,
    private val genreRepository: Repository<Genre>,
    private val service: ItunesService,
    private val scheduler: SchedulersProvider
) : Repository<Podcast> {

    override fun add(element: Podcast): Completable =
        Completable.fromCallable {
            dao.upsertPodcastWrapperTransaction(element.toWrapperEntity())
        }

    override fun addAll(elements: List<Podcast>): Completable =
        Completable.fromCallable {
            dao.upsertPodcastWrapperTransaction(elements.map { it.toWrapperEntity() })
        }

    override fun remove(element: Podcast) = dao.delete(element.toEntity())

    override fun findById(id: Any) =
        genreRepository.getAll()
            .flatMapMaybe { genreList ->
                dao.findById(id as Long)
                    .map { it.toDomain(genreList) }
                    /** Get from the API if the cache is empty */
                    .switchIfEmpty(
                        service.getPodcastById(id)
                            .map { it.toDomain(genreList) }
                            .flatMap { add(it).andThen(Single.just(it)) }
                            .toMaybe()
                    )
            }

    override fun findByIds(ids: List<Any>): Single<List<Podcast>> {
        val podcastIds = ids.map { it as Long }

        return genreRepository.getAll()
            .flatMap { genreList ->
                dao.findByIds(podcastIds)
                    .map { it.map { podcast -> podcast.toDomain(genreList) } }
                    .flatMap { cache ->
                        val missingIds = podcastIds.filter { id -> cache.none { it.id == id } }
                        val calls = missingIds.map { findById(it).subscribeOn(scheduler.io()) }

                        Maybe.merge(calls)
                            .toList()
                            .map { cache + it }
                    }
            }
    }

    override fun getAll(): Single<List<Podcast>> = genreRepository.getAll()
        .flatMap { genreList ->
            dao.getAll().map { podcasts -> podcasts.map { it.toDomain(genreList) } }
        }

    override fun clear(): Completable = dao.deleteAll()

}