package ismaeldivita.audioma.podcast.service.itunes.parser.json.model

internal data class SearchJson (
    val resultCount: Int,
    val results: List<PodcastJson> = emptyList()
)