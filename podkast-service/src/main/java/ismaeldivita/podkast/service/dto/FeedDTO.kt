package ismaeldivita.podkast.service.dto

data class FeedDTO(
        val description: String,
        val languageIso639: String,
        val episodes: List<EpisodeDTO>
)
