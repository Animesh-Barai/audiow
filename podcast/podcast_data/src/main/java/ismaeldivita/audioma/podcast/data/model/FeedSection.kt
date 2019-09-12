package ismaeldivita.audioma.podcast.data.model

sealed class FeedSection {

    object Foo : FeedSection()

    data class GenreSection(val genre: Genre, val podcasts: List<Podcast>) : FeedSection()

}