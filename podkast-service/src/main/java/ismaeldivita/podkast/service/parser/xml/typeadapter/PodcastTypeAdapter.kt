package ismaeldivita.podkast.service.parser.xml.typeadapter

import com.tickaroo.tikxml.TikXmlConfig
import com.tickaroo.tikxml.XmlReader
import com.tickaroo.tikxml.XmlWriter
import com.tickaroo.tikxml.typeadapter.TypeAdapter
import ismaeldivita.podkast.service.model.Episode
import ismaeldivita.podkast.service.model.Podcast
import ismaeldivita.podkast.service.parser.xml.model.EpisodeXml
import ismaeldivita.podkast.service.parser.xml.model.RssXml

internal class PodcastTypeAdapter : TypeAdapter<Podcast> {

    override fun toXml(
            writer: XmlWriter,
            config: TikXmlConfig,
            value: Podcast,
            overridingXmlElementTagName: String
    ) = throw NotImplementedError()


    override fun fromXml(reader: XmlReader, config: TikXmlConfig): Podcast {
        val adapter = config.getTypeAdapter(RssXml::class.java)
        val rss = adapter.fromXml(reader, config)

        return Podcast(
                title = rss.podcast.title,
                description = rss.podcast.description ?: rss.podcast.summary.orEmpty(),
                episodes = mapEpisodes(rss.podcast.episodesXml)
        )
    }

    private fun mapEpisodes(episodesXml: List<EpisodeXml>): List<Episode> =
            episodesXml.map {
                Episode(
                        title = it.title,
                        description = it.description ?: it.summary ?: it.subtitle.orEmpty(),
                        audioFileUrl = it.audioFile.url,
                        duration = it.duration,
                        isExplicit = it.explicit,
                        publicationDate = it.pubDate,
                        coverImageUrl = it.coverUrl
                )
            }
}
