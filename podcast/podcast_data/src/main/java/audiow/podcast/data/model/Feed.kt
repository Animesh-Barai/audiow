package audiow.podcast.data.model

data class Feed(
    val podcastId: Long,
    val description: String,
    val language: String,
    val episodes: List<Episode>
)