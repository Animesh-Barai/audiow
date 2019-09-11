package ismaeldivita.audioma.podcast.data.repository

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.podcast.data.model.FeedSection

class FeedRepository : Repository<FeedSection> {

    override fun add(element: FeedSection): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addAll(elements: List<FeedSection>): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): Single<List<FeedSection>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(id: Any): Maybe<FeedSection> = throw UnsupportedOperationException()


    override fun remove(element: FeedSection): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}