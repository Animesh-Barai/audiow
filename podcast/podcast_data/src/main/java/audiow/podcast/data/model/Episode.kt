package audiow.podcast.data.model

import java.util.*

data class Episode(
    val id: String,
    val title: String,
    val subtitle: String?,
    val description: String,
    val audioFileUrl: String,
    val duration: String?,
    val isExplicit: Boolean,
    val publicationDateRFC822: String,
    val publicationDate: Date?,
    val coverImageUrl: String?
)