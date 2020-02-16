package audiow.podcast.service.itunes.parser.json.typeadapter

import android.annotation.SuppressLint
import audiow.core.monitoring.log.Logger
import com.squareup.moshi.FromJson
import audiow.podcast.service.itunes.model.ItunesPodcast
import audiow.podcast.service.itunes.parser.json.model.PodcastJson

internal object PodcastTypeAdapter {

    private const val ERROR_TAG = "Itunes podcast parser"

    @FromJson
    fun fromJson(json: List<PodcastJson>): List<ItunesPodcast> =
            json.mapNotNull(PodcastTypeAdapter::fromJson)

    @FromJson
    fun fromJson(json: PodcastJson): ItunesPodcast? = try {
        ItunesPodcast(
                id = requireNotNull(json.trackId) { "$ERROR_TAG - trackId is null" },
                title = requireNotNull(json.trackName) { "$ERROR_TAG - title is null" },
                artistName = requireNotNull(json.artistName) { "$ERROR_TAG - artistName is null" },
                rssUrl = requireNotNull(json.feedUrl) { "$ERROR_TAG - feedUrl is null" },
                artworkList = json.artworkList,
                primaryGenreId = getPrimaryGenreId(json),
                genreListId = json.genreIds,
                explicit = getExplicit(json)
        )
    } catch (error: Throwable) {
        Logger.e(error.message ?: ERROR_TAG, mapOf(
                "id" to json.trackId,
                "title" to json.trackName,
                "message" to error.message
        ))
        null
    }

    private fun getPrimaryGenreId(json: PodcastJson): Long =
            json.genreIds
                    .zip(json.genres)
                    .first { it.second == json.primaryGenreName }
                    .first

    @SuppressLint("DefaultLocale")
    private fun getExplicit(json: PodcastJson): Boolean =
            requireNotNull(json.trackExplicitness) { "$ERROR_TAG - trackExplicitness is null" }
                    .toUpperCase()
                    // Possible values to explicit are [notExplicit, cleaned, explicit]
                    .contentEquals("EXPLICIT")

}
