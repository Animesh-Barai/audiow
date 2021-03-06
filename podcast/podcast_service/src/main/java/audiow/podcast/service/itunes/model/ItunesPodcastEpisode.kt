package audiow.podcast.service.itunes.model

data class ItunesPodcastEpisode(
    val title: String,
    val subtitle: String?,
    val description: String,
    val audioFileUrl: String,
    val duration: String?,
    val isExplicit: Boolean,
    val publicationDateRFC822: String,
    val coverImageUrl: String?
)
