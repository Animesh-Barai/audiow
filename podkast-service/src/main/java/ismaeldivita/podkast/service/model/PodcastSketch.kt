package ismaeldivita.podkast.service.model

data class PodcastSketch(
        val title: String,
        val artistName: String,
        val rssUrl: String,
        val artworkList: List<Artwork>,
        val primaryGenre: Genre?,
        val genreList: List<Genre>,
        val explicit: Boolean
)
