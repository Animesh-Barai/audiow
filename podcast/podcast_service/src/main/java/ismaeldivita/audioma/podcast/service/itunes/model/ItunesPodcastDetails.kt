package ismaeldivita.audioma.podcast.service.itunes.model

data class ItunesPodcastDetails(
    val description: String,
    val languageIso639: String,
    val episodes: List<ItunesPodcastEpisode>
)
