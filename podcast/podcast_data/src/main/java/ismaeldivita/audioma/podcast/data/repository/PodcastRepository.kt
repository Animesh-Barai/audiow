package ismaeldivita.audioma.podcast.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import ismaeldivita.audioma.core.data.Repository
import ismaeldivita.audioma.podcast.data.model.Podcast

class PodcastRepository : Repository<Podcast> {

    override fun add(element: Podcast): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): Single<List<Podcast>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(element: Podcast): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}