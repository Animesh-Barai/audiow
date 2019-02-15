package ismaeldivita.podkast.service.dto

data class GenreDTO(
        val id: Int,
        val name: String,
        val detail: GenreDetailDTO? = null
)
