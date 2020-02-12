package audiow.podcast.service.itunes.parser.json.model

import audiow.podcast.service.itunes.model.ItunesArtwork

internal data class PodcastJson(
        val wrapperType: String,
        val kind: String,
        val artistId: Int?,
        val collectionId: Int,
        val trackId: Long,
        val artistName: String,
        val collectionName: String,
        val trackName: String,
        val collectionCensoredName: String,
        val trackCensoredName: String,
        val feedUrl: String,
        val releaseDate: String,
        val collectionExplicitness: String,
        val trackExplicitness: String,
        val trackCount: Int,
        val country: String,
        val primaryGenreName: String,
        val genreIds: List<Long>,
        val genres: List<String>,
        val artworkList: List<ItunesArtwork> = emptyList()
)
