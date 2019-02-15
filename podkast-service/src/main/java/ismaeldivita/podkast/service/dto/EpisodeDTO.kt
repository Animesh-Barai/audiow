package ismaeldivita.podkast.service.dto

import java.util.*

data class EpisodeDTO(
        val title: String,
        val description: String,
        val audioFileUrl: String,
        val duration: String,
        val isExplicit: Boolean,
        val publicationDate: Date?,
        val coverImageUrl: String?
)
