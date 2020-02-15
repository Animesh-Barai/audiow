package audiow.podcast.service.itunes.parser.json.model

import audiow.podcast.service.itunes.model.ItunesArtwork

/**
 * Network model for the Itunes podcast
 *
 * The API is returning null for some fields that were not supposed to be optional
 * Let's keep all the properties nullable and try a safer approach on the parser adapter
 */
internal data class PodcastJson(
        val wrapperType: String?,
        val kind: String?,
        val artistId: Int?,
        val collectionId: Int?,
        val trackId: Long?,
        val artistName: String?,
        val collectionName: String?,
        val trackName: String?,
        val collectionCensoredName: String?,
        val trackCensoredName: String?,
        val feedUrl: String?,
        val releaseDate: String?,
        val collectionExplicitness: String?,
        val trackExplicitness: String?,
        val trackCount: Int?,
        val country: String?,
        val primaryGenreName: String?,
        val genreIds: List<Long> = emptyList(),
        val genres: List<String> = emptyList(),
        val artworkList: List<ItunesArtwork> = emptyList()
)
