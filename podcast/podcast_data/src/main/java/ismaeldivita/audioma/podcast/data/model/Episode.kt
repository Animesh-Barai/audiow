package ismaeldivita.audioma.podcast.data.model

data class Episode(
    val title: String,
    val description: String,
    val audioFileUrl: String,
    val duration: String,
    val isExplicit: Boolean,
    val publicationDate: String?,
    val coverImageUrl: String
)