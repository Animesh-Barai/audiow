package ismaeldivita.audioma.podcast.service.itunes.model

data class ItunesPodcastFeed(
    val description: String,
    val languageIso639: String,
    val episodes: List<ItunesPodcastEpisode>,
    val lastModified: String? = null,
    val eTag: String? = null
)
