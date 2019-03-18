package ismaeldivita.podkast.service.itunes.parser.json.typeadapter

import com.squareup.moshi.FromJson
import ismaeldivita.podkast.service.itunes.model.ItunesPodcast
import ismaeldivita.podkast.service.itunes.parser.json.model.PodcastJson

internal object PodcastTypeAdapter {

    @FromJson
    fun fromJson(json: List<PodcastJson>): List<ItunesPodcast> = json.map(
        PodcastTypeAdapter::fromJson)

    @FromJson
    fun fromJson(json: PodcastJson): ItunesPodcast = with(json) {
        ItunesPodcast(
            id = trackId,
            title = trackName,
            artistName = artistName,
            rssUrl = feedUrl,
            artworkList = artworkList,
            primaryGenreId = getPrimaryGenreId(this),
            genreListId = genreIds,
            explicit = getExplicit(this)
        )
    }

    private fun getPrimaryGenreId(json: PodcastJson): Int =
        json.genreIds
            .zip(json.genres)
            .first { it.second == json.primaryGenreName }
            .first


    private fun getExplicit(json: PodcastJson): Boolean =
            json.trackExplicitness
                    .toUpperCase()
                    // Possible values to explicit are (notExplicit | cleaned | explicit)
                    .contentEquals("EXPLICIT")

}
