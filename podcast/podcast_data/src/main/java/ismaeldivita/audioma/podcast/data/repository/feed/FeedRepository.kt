package ismaeldivita.audioma.podcast.data.repository.feed

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.data.repository.RepositoryWatcher
import ismaeldivita.audioma.core.util.time.RFC822DateParser
import ismaeldivita.audioma.podcast.data.model.Feed
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.storage.database.dao.FeedDAO
import ismaeldivita.audioma.podcast.service.itunes.ItunesService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class FeedRepository @Inject constructor(
    private val dao: FeedDAO,
    private val dateParser: RFC822DateParser,
    private val podcastRepository: Repository<Podcast>,
    private val itunesService: ItunesService
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
            fetchFromRemote(podcastId).toObservable(),

            dao.onItemChanged(podcastId)
                .map { it.toDomain(dateParser) }
        )
    }

    override fun onChanged(): Observable<List<Feed>> =
        dao.onChanged().map { feedList ->
            feedList.map { it.toDomain(dateParser) }
        }

    // TODO extract to a class
    private fun fetchFromRemote(id: Long): Completable =
        podcastRepository.findById(id)
            .toSingle()
            .flatMap { podcast ->
                dao.findById(podcast.id).map { it to podcast }
                    .flatMap { (feedWrapper, podcast) ->
                        val metadata = feedWrapper.feed.metadata

                        itunesService.getPodcastRss(
                            rssUrl = podcast.rssUrl,
                            ifModifiedSince = metadata.lastModified,
                            ifNoneMatch = metadata.eTag
                        ).toMaybe()
                    }
                    .switchIfEmpty(itunesService.getPodcastRss(podcast.rssUrl))
            }.flatMapCompletable { feedNetworkModel ->
                Completable.fromCallable {
                    dao.insert(
                        feedNetworkModel.toEntity(id),
                        feedNetworkModel.episodes.map { it.toEpisodeEntity(id) })
                }
            }
}