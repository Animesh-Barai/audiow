package ismaeldivita.audioma.podcast.data.model

sealed class DiscoverItem {

    data class Banner(val podcasts: List<Podcast>) : DiscoverItem()

    data class Highlight(val podcast: Podcast) : DiscoverItem()

    data class GenreSection(val genre: Genre, val podcasts: List<Podcast>) : DiscoverItem()

}