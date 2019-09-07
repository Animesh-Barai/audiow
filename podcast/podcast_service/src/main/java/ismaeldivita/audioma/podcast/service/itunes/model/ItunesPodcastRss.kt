package ismaeldivita.audioma.podcast.service.itunes.model

data class ItunesPodcastRss(
    val description: String,
    val languageIso639: String,
    val episodes: List<ItunesPodcastEpisode>
)
