package ismaeldivita.audioma.podcast.data.repository.feed

import android.util.Log
import io.reactivex.Completable
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.monitoring.log.Logger
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.storage.database.dao.FeedDAO
import ismaeldivita.audioma.podcast.service.itunes.ItunesService
import ismaeldivita.audioma.podcast.service.util.HeaderKey
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
                        val lastModified = headers.get(HeaderKey.lastModified)
                        val eTag = headers.get(HeaderKey.eTag)

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