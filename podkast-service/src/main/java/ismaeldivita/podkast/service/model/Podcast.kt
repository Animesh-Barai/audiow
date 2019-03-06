package ismaeldivita.podkast.service.model

data class Podcast(
        val id: Int,
        val title: String,
        val artistName: String,
        val rssUrl: String,
        val artworkList: List<Artwork>,
        val primaryGenre: Genre,
        val genreList: List<Genre>,
        val explicit: Boolean
)
