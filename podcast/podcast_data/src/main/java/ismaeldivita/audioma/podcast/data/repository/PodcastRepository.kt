package ismaeldivita.audioma.podcast.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import ismaeldivita.audioma.core.data.Repository
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.storage.database.dao.PodcastDAO
import javax.inject.Inject

internal class PodcastRepository @Inject constructor(
    private val podcastDAO: PodcastDAO
) : Repository<Podcast> {

    override fun add(element: Podcast): Completable = throw UnsupportedOperationException()

    override fun remove(element: Podcast): Completable = throw UnsupportedOperationException()

    override fun getAll(): Single<List<Podcast>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear(): Completable = podcastDAO.deleteAll()

}