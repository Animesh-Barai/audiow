package audiow.podcast.data.repository.podcast

import io.reactivex.Completable
import io.reactivex.Single
import audiow.core.data.repository.Repository
import audiow.podcast.data.model.Genre
import audiow.podcast.data.model.Podcast
import audiow.podcast.data.storage.database.dao.PodcastDAO
import toDomain
import toEntity
import toWrapperEntity
import javax.inject.Inject

internal class PodcastRepository @Inject constructor(
    private val dao: PodcastDAO,
    private val genreRepository: Repository<Genre>
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

    // TODO fetch from Itunes when empty
    override fun findById(id: Any) =
        genreRepository.getAll()
            .flatMapMaybe { genreList -> dao.findById(id as Long).map { it.toDomain(genreList) } }

    override fun findByIds(ids: List<Any>): Single<List<Podcast>> =
        genreRepository.getAll()
            .flatMap { genreList ->
                dao.findByIds(ids.map { it as Long })
                    .map { it.map { podcast -> podcast.toDomain(genreList) } }
            }

    override fun getAll(): Single<List<Podcast>> = genreRepository.getAll()
        .flatMap { genreList ->
            dao.getAll().map { podcasts -> podcasts.map { it.toDomain(genreList) } }
        }

    override fun clear(): Completable = dao.deleteAll()

}