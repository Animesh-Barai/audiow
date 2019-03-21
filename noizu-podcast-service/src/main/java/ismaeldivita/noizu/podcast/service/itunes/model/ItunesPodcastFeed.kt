package ismaeldivita.noizu.podcast.service.itunes.model

data class ItunesPodcastFeed(
    val description: String,
    val languageIso639: String,
    val episodes: List<ItunesPodcastEpisode>
)
