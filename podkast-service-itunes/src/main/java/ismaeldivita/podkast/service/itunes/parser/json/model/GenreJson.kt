package ismaeldivita.podkast.service.itunes.parser.json.model

internal data class GenreJson(
        val id: Int,
        val name: String,
        val rssUrls: Map<String, String>,
        val subgenres: Map<String, GenreJson> = emptyMap()
)
