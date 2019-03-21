package ismaeldivita.audioma.podcast.data.repository

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesPodcast

class PodcastRepository : Repository<ItunesPodcast> {

    override fun add(element: ItunesPodcast): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): Single<List<ItunesPodcast>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getById(id: Int): Maybe<ItunesPodcast> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(element: ItunesPodcast): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}