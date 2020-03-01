package audiow.podcast.service.itunes.parser.json.model

internal data class RootResultJson (
    val resultCount: Int,
    val results: List<PodcastJson> = emptyList()
)
