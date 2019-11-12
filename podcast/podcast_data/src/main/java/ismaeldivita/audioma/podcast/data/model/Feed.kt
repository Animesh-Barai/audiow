package ismaeldivita.audioma.podcast.data.model

data class Feed(
    val description: String,
    val languageIso639: String,
    val episodes: List<Episode>
)