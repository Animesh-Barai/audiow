package ismaeldivita.audioma.podcast.data.repository.podcast

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.storage.database.dao.PodcastDAO
import toDomain
import toEntity
import toWrapperEntity
import javax.inject.Inject

internal class PodcastRepository @Inject constructor(
    private val dao: PodcastDAO,
    private val genreRepository: Repository<Genre>
) : Repository<Podcast> {

    override fun onChanged(id: Any): Observable<List<Podcast>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemChanged(id: Any): Observable<Podcast> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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