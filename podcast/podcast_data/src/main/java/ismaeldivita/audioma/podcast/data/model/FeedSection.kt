package ismaeldivita.audioma.podcast.data.model

sealed class FeedSection {

    data class GenreSection(val genre: Genre, val podcasts: List<Podcast>) : FeedSection()

}