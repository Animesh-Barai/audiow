package ismaeldivita.podkast.service.model

enum class SearchAttribute(val attribute: String) {
    PODCAST_TITLE("titleTerm"),
    ARTIST_NAME("artistTerm"),
    DESCRIPTION("descriptionTerm")
}
