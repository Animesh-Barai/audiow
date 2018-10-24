package ismaeldivita.podkast.service.parser.json.typeadapter.model

internal data class GenreNodeJson(
        val id: Int,
        val name: String,
        val rssUrls: Map<String, String>,
        val subgenres: Map<String, GenreNodeJson> = emptyMap()
)
