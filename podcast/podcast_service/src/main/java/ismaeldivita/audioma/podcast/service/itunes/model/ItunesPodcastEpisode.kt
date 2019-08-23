package ismaeldivita.audioma.podcast.service.itunes.model

import java.util.*

data class ItunesPodcastEpisode(
    val title: String,
    val description: String,
    val audioFileUrl: String,
    val duration: String,
    val isExplicit: Boolean,
    val publicationDate: Date?,
    val coverImageUrl: String?
)
