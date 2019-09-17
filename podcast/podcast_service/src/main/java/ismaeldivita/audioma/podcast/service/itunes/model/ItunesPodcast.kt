package ismaeldivita.audioma.podcast.service.itunes.model

data class ItunesPodcast(
    val id: Long,
    val title: String,
    val artistName: String,
    val rssUrl: String,
    val artworkList: List<ItunesArtwork>,
    val primaryGenreId: Long,
    val genreListId: List<Long>,
    val explicit: Boolean
)
