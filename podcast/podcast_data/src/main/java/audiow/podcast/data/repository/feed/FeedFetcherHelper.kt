package audiow.podcast.data.repository.feed

import io.reactivex.Completable
import audiow.core.data.repository.Repository
import audiow.core.monitoring.log.Logger
import audiow.podcast.data.model.Podcast
import audiow.podcast.data.storage.database.dao.FeedDAO
import audiow.podcast.service.itunes.ItunesService
import audiow.podcast.service.util.HeaderKey
import java.io.IOException
import javax.inject.Inject

internal class FeedFetcherHelper @Inject constructor(
    private val podcastRepository: Repository<Podcast>,
    private val itunesService: ItunesService,
    private val dao: FeedDAO
) : (Long) -> Completable {

    override fun invoke(podcastId: Long): Completable =
        podcastRepository.findById(podcastId)
            .toSingle()
            .flatMap { podcast ->

                /**
                 * Search if the podcast contains a cached feed to fetch a new one using the
                 * eTag/lastModified headers. This headers will save network bandwidth when the
                 * feed is already updated.
                 **/
                dao.findById(podcast.id).map { it to podcast }
                    .flatMap { (feedWrapper, podcast) ->
                        val metadata = feedWrapper.feed.metadata

                        itunesService.getPodcastRss(
                            rssUrl = podcast.rssUrl,
                            ifModifiedSince = metadata?.lastModified,
                            ifNoneMatch = metadata?.eTag
                        ).toMaybe()
                    }
                    .switchIfEmpty(itunesService.getPodcastRss(podcast.rssUrl))

            }.flatMapCompletable { response ->
                when {
                    response.isSuccessful -> {
                        val headers = response.headers()
                        val feedNetworkModel = response.body()!!
                        val lastModified = headers.get(HeaderKey.LAST_MODIFIED)
                        val eTag = headers.get(HeaderKey.E_TAG)

                        Logger.d(
                            "Feed updated",
                            mapOf("id" to podcastId, "lastModified" to lastModified, "eTag" to eTag)
                        )

                        Completable.fromCallable {
                            dao.insert(
                                feedNetworkModel.toEntity(podcastId, lastModified, eTag),
                                feedNetworkModel.episodes.map { it.toEpisodeEntity(podcastId) })
                        }
                    }

                    response.code() == 304 -> {
                        Logger.d("Feed not changed since last updated", mapOf("id" to podcastId))
                        Completable.complete()
                    }

                    else -> {
                        Logger.d("Failed to fetch Feed", mapOf("id" to podcastId))
                        Completable.error(IOException("Failed to fetch Feed"))
                    }
                }
            }
}