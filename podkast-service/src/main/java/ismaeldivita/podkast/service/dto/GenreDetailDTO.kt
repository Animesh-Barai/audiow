package ismaeldivita.podkast.service.dto

data class GenreDetailDTO(
        val subgenres: List<GenreDTO>,
        val topPodcastsUrl: String
)
