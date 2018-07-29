package ismaeldivita.podkast.service.model.internal

internal data class SearchJson (
    val resultCount: Int,
    val results: List<PodcastJson>
)
