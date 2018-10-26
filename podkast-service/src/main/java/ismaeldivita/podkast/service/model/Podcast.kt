package ismaeldivita.podkast.service.model

data class Podcast(
        val title: String,
        val description: String,
        val languageIso639: String,
        val episodes: List<Episode>
)
