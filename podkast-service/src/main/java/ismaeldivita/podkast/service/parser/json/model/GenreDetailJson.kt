package ismaeldivita.podkast.service.parser.json.model

internal data class GenreDetailJson(
        val id: Int,
        val name: String,
        val rssUrls: Map<String, String>,
        val subgenres: Map<String, GenreDetailJson> = emptyMap()
)
