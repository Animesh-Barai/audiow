package ismaeldivita.audioma.podcast.data.repository.feed

import io.reactivex.Single
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.storage.database.dao.FeedDAO
import ismaeldivita.audioma.podcast.service.itunes.ItunesService
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesPodcastFeed
import javax.inject.Inject

internal class FeedFetcherHelper @Inject constructor(
    private val podcastRepository: Repository<Podcast>,
    private val feedDao: FeedDAO,
    private val itunesService: ItunesService
) : (@ParameterName("podcastId") Long) -> Single<ItunesPodcastFeed> {

    override fun invoke(podcastId: Long): Single<ItunesPodcastFeed> =
        podcastRepository.findById(podcastId)
            .flatMap { podcast ->
                feedDao.findById(podcast.id).map { it to podcast }
                    .flatMap { (feedWrapper, podcast) ->
                        val metadata = feedWrapper.feed.metadata

                        itunesService.getPodcastRss(
                            rssUrl = podcast.rssUrl,
                            ifModifiedSince = metadata.lastModified,
                            ifNoneMatch = metadata.eTag
                        ).toMaybe()
                    }
                    .switchIfEmpty(itunesService.getPodcastRss(podcast.rssUrl).toMaybe())
            }.toSingle()
}