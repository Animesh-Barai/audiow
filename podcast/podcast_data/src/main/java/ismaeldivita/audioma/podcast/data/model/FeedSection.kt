package ismaeldivita.audioma.podcast.data.model

sealed class FeedSection {

    data class Banner(val podcasts: List<Podcast>) : FeedSection()

    data class Highlight(val podcast: Podcast) : FeedSection()

    data class GenreSection(val genre: Genre, val podcasts: List<Podcast>) : FeedSection()

}