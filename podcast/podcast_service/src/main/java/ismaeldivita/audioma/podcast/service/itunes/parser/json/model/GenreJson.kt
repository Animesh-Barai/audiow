package ismaeldivita.audioma.podcast.service.itunes.parser.json.model

internal data class GenreJson(
        val id: Long,
        val name: String,
        val rssUrls: Map<String, String>,
        val subgenres: Map<String, GenreJson> = emptyMap()
)
