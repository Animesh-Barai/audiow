package ismaeldivita.audioma.podcast.data.model

data class Feed(
    val podcast: Podcast,
    val description: String,
    val language: String,
    val episodes: List<Episode>
)