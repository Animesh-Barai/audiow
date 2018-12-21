package ismaeldivita.podkast.service.model

data class Feed(
        val description: String,
        val languageIso639: String,
        val episodes: List<Episode>
)
