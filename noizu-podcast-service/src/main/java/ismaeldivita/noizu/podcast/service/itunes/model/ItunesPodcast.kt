package ismaeldivita.noizu.podcast.service.itunes.model

data class ItunesPodcast(
    val id: Int,
    val title: String,
    val artistName: String,
    val rssUrl: String,
    val artworkList: List<ItunesArtwork>,
    val primaryGenreId: Int,
    val genreListId: List<Int>,
    val explicit: Boolean
)
