package ismaeldivita.audioma.podcast.data.repository.feed

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.util.time.RFC822DateParser
import ismaeldivita.audioma.podcast.data.model.Episode
import ismaeldivita.audioma.podcast.data.storage.database.dao.FeedDAO

internal class EpisodeRepository(
    private val feedDAO: FeedDAO,
    private val dateParser: RFC822DateParser
) : Repository<Episode> {

    override fun findById(id: Any): Maybe<Episode> =
        feedDAO.findEpisodeById(id as String)
            .map { it.toDomain(dateParser) }

    override fun findByIds(ids: List<Any>): Single<List<Episode>> =
        feedDAO.findEpisodesByIds(ids.filterIsInstance<String>())
            .map { episodes -> episodes.map { it.toDomain(dateParser) } }

    override fun getAll(): Single<List<Episode>> =
        feedDAO.getAllEpisodes()
            .map { episodes -> episodes.map { it.toDomain(dateParser) } }

    /** Clear delegated to [FeedRepository] */
    override fun clear() = Completable.complete()

    override fun add(element: Episode): Completable = throw UnsupportedOperationException()

    override fun addAll(elements: List<Episode>): Completable =
        throw UnsupportedOperationException()

    override fun remove(element: Episode): Completable = throw UnsupportedOperationException()

}