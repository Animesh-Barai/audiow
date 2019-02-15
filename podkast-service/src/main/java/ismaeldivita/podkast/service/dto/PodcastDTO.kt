package ismaeldivita.podkast.service.dto

data class PodcastDTO(
        val id: Int,
        val title: String,
        val artistName: String,
        val rssUrl: String,
        val artworkList: List<ArtworkDTO>,
        val primaryGenre: GenreDTO,
        val genreList: List<GenreDTO>,
        val genreMap: Map<Int, String>,
        val explicit: Boolean
)
