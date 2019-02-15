package ismaeldivita.podkast.service.parser.json.typeadapter

import com.squareup.moshi.FromJson
import ismaeldivita.podkast.service.dto.GenreDTO
import ismaeldivita.podkast.service.dto.GenreDTOTree.Companion.ROOT_GENRE_ID
import ismaeldivita.podkast.service.dto.PodcastDTO
import ismaeldivita.podkast.service.parser.json.model.PodcastJson

internal object PodcastTypeAdapter {

    @FromJson
    fun fromJson(json: List<PodcastJson>): List<PodcastDTO> = json.map(PodcastTypeAdapter::fromJson)

    @FromJson
    fun fromJson(json: PodcastJson): PodcastDTO = with(json) {
        PodcastDTO(
                id = trackId,
                title = trackName,
                artistName = artistName,
                rssUrl = feedUrl,
                artworkList = artworkList,
                primaryGenre = getPrimaryGenre(this),
                genreMap =  getGenreMap(this),
                genreList = getGenreList(this),
                explicit = getExplicit(this)
        )
    }

    private fun getPrimaryGenre(json: PodcastJson): GenreDTO = with(json) {
        getGenreList(this)
                .first { it.name.compareTo(primaryGenreName, ignoreCase = true) == 0 }
    }

    private fun getGenreList(json: PodcastJson): List<GenreDTO> = with(json) {
        genreIds.zip(genres)
                // All podcasts have this genre, which represent the Podcast category,
                // so to avoid redundancy we're filtering this category
                .filter { it.first != ROOT_GENRE_ID }
                .map { GenreDTO(it.first, it.second) }
    }

    private fun getGenreMap(json: PodcastJson): Map<Int, String> = json.genreIds.zip(json.genres).toMap()

    private fun getExplicit(json: PodcastJson): Boolean =
            json.trackExplicitness
                    .toUpperCase()
                    // Possible values to explicit are (notExplicit | cleaned | explicit)
                    .contentEquals("EXPLICIT")

}
