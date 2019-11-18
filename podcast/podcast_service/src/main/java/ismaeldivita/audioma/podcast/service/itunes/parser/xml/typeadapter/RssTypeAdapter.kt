package ismaeldivita.audioma.podcast.service.itunes.parser.xml.typeadapter

import com.tickaroo.tikxml.TikXmlConfig
import com.tickaroo.tikxml.XmlReader
import com.tickaroo.tikxml.XmlWriter
import com.tickaroo.tikxml.typeadapter.TypeAdapter
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesPodcastEpisode
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesPodcastFeed
import ismaeldivita.audioma.podcast.service.itunes.parser.xml.model.EpisodeXml
import ismaeldivita.audioma.podcast.service.itunes.parser.xml.model.RssXml

internal class RssTypeAdapter : TypeAdapter<ItunesPodcastFeed> {

    override fun toXml(
        writer: XmlWriter,
        config: TikXmlConfig,
        value: ItunesPodcastFeed,
        overridingXmlElementTagName: String
    ) = throw NotImplementedError()

    override fun fromXml(reader: XmlReader, config: TikXmlConfig): ItunesPodcastFeed {
        val adapter = config.getTypeAdapter(RssXml::class.java)
        val rss = adapter.fromXml(reader, config)

        return ItunesPodcastFeed(
            description = rss.detail.description ?: rss.detail.summary.orEmpty(),
            languageIso639 = rss.detail.language,
            episodes = mapEpisodes(rss.detail.episodesXml)
        )
    }

    private fun mapEpisodes(episodesXml: List<EpisodeXml>): List<ItunesPodcastEpisode> =
            episodesXml.map {
                ItunesPodcastEpisode(
                    title = it.title,
                    description = it.description ?: it.summary ?: it.subtitle.orEmpty(),
                    audioFileUrl = it.audioFile.url,
                    duration = it.duration,
                    isExplicit = it.explicit,
                    publicationDateRFC822 = it.pubDate,
                    coverImageUrl = it.coverUrl
                )
            }

}
