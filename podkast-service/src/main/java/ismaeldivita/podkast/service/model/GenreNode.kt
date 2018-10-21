package ismaeldivita.podkast.service.model

data class GenreNode(
        val genre: Genre,
        val subgenres: List<GenreNode>,
        val topPodcastsUrl: String
)
