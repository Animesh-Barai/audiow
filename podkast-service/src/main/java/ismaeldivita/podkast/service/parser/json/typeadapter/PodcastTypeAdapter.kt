package ismaeldivita.podkast.service.parser.json.typeadapter

import com.squareup.moshi.FromJson
import ismaeldivita.podkast.service.model.Genre
import ismaeldivita.podkast.service.model.Podcast
import ismaeldivita.podkast.service.parser.json.model.PodcastJson

internal object PodcastTypeAdapter {

    @FromJson
    fun fromJson(json: List<PodcastJson>): List<Podcast> = json.map(PodcastTypeAdapter::fromJson)

    @FromJson
    fun fromJson(json: PodcastJson): Podcast = with(json) {
        Podcast(
                id = trackId,
                title = trackName,
                artistName = artistName,
                rssUrl = feedUrl,
                artworkList = artworkList,
                primaryGenre = getPrimaryGenre(this),
                genreList = getGenreList(this),
                explicit = getExplicit(this)
        )
    }

    private fun getPrimaryGenre(json: PodcastJson): Genre = with(json) {
        getGenreList(this)
                .first { it.name.compareTo(primaryGenreName, ignoreCase = true) == 0 }
    }

    private fun getGenreList(json: PodcastJson): List<Genre> = with(json) {
        genreIds.zip(genres)
                // All podcasts have this genre, which represent the Podcast category,
                // so to avoid redundancy we're filtering this category
                .filter { it.first != 26 }
                .map { Genre(it.first, it.second) }
    }

    private fun getExplicit(json: PodcastJson): Boolean =
            json.trackExplicitness
                    .toUpperCase()
                    // Possible values to explicit are (notExplicit | cleaned | explicit)
                    .contentEquals("EXPLICIT")

}
