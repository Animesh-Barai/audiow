package ismaeldivita.podkast.service.parser.typeadapter.model

internal data class SearchJson (
    val resultCount: Int,
    val results: List<PodcastJson> = emptyList()
)
