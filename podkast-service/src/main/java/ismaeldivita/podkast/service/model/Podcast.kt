package ismaeldivita.podkast.service.model

data class Podcast(
        val title: String,
        val artistName: String,
        val rssUrl: String,
        val artworkList: List<Artwork>,
        val primaryGenre: Genre,
        val genreList: List<Genre>,
        val explicit: Boolean,
        val detail: PodcastDetail? = null
)
