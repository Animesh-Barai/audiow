package ismaeldivita.audioma.podcast.data.model

import java.util.*

data class Episode(
    val title: String,
    val description: String,
    val audioFileUrl: String,
    val duration: String,
    val isExplicit: Boolean,
    val publicationDateRFC822: String,
    val publicationDate: Date?,
    val coverImageUrl: String?
)