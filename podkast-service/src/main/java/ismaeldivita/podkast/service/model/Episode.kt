package ismaeldivita.podkast.service.model

import java.util.*

data class Episode(
        val title: String,
        val description: String,
        val audioFileUrl: String,
        val duration: String,
        val isExplicit: Boolean,
        val publicationDate: Date?,
        val coverImageUrl: String?
)
