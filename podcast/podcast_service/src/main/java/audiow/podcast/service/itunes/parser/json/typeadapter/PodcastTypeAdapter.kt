package audiow.podcast.service.itunes.parser.json.typeadapter

import android.annotation.SuppressLint
import audiow.core.monitoring.log.Logger
import com.squareup.moshi.FromJson
import audiow.podcast.service.itunes.model.ItunesPodcast
import audiow.podcast.service.itunes.parser.json.model.PodcastJson

internal object PodcastTypeAdapter {

    @FromJson
    fun fromJson(json: List<PodcastJson>): List<ItunesPodcast> =
            json.mapNotNull(PodcastTypeAdapter::fromJson)

    @FromJson
    fun fromJson(json: PodcastJson): ItunesPodcast? = try {
        ItunesPodcast(
                id = json.trackId!!,
                title = json.trackName!!,
                artistName = json.artistName!!,
                rssUrl = json.feedUrl!!,
                artworkList = json.artworkList,
                primaryGenreId = getPrimaryGenreId(json),
                genreListId = json.genreIds,
                explicit = getExplicit(json)
        )
    } catch (error: Throwable) {
        Logger.e("Itunes podcast parser", mapOf(
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
            json.trackExplicitness!!
                    .toUpperCase()
                    // Possible values to explicit are [notExplicit, cleaned, explicit]
                    .contentEquals("EXPLICIT")

}
