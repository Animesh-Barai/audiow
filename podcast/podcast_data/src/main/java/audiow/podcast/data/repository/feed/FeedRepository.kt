package audiow.podcast.data.repository.feed

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import audiow.core.data.repository.Repository
import audiow.core.data.repository.RepositoryWatcher
import audiow.core.util.time.RFC822DateParser
import audiow.podcast.data.model.Feed
import audiow.podcast.data.storage.database.dao.FeedDAO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FeedRepository @Inject constructor(
    private val dao: FeedDAO,
    private val dateParser: RFC822DateParser,
    private val feedFetcherHelper: FeedFetcherHelper
) : Repository<Feed>, RepositoryWatcher<Feed> {

    override fun add(element: Feed) = Completable.fromCallable {
        dao.insert(element.toEntity(), element.episodes.map { it.toEntity(element.podcastId) })
    }

    override fun addAll(elements: List<Feed>) = Completable.fromCallable {
        val feedPair = elements.map {
            it.toEntity() to it.episodes.map { ep -> ep.toEntity(it.podcastId) }
        }
        dao.insert(feedPair.map { it.first }, feedPair.map { it.second }.flatten())
    }

    override fun getAll(): Single<List<Feed>> =
        dao.getAllFeeds().map { feeds -> feeds.map { it.toDomain(dateParser) } }

    override fun findById(id: Any): Maybe<Feed> =
        dao.findById(id as Long).map { it.toDomain(dateParser) }

    override fun findByIds(ids: List<Any>): Single<List<Feed>> =
        dao.findByIds(ids.filterIsInstance<Long>())
            .map { feeds -> feeds.map { it.toDomain(dateParser) } }

    override fun remove(element: Feed): Completable = dao.delete(element.toEntity())

    override fun clear(): Completable = dao.deleteAll()

    override fun onItemChanged(id: Any): Observable<Feed> {
        val podcastId = id as Long

        return Observable.merge(
            feedFetcherHelper(podcastId).toObservable(),

            dao.onItemChanged(podcastId)
                .map { it.toDomain(dateParser) }
        )
    }

    override fun onChanged(): Observable<List<Feed>> =
        dao.onChanged().map { feedList ->
            feedList.map { it.toDomain(dateParser) }
        }
}