package ismaeldivita.podkast.service.model

data class Genre(
        val id: Int,
        val name: String,
        val subGenres: List<Genre>?
)
