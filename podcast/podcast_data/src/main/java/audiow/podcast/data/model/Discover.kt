package audiow.podcast.data.model

sealed class Discover {

    data class Banner(val podcasts: List<Podcast>) : Discover()

    data class Highlight(val podcast: Podcast) : Discover()

    data class GenreSection(val genre: Genre, val podcasts: List<Podcast>) : Discover()

}