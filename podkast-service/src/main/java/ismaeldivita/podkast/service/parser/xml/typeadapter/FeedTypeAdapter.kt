package ismaeldivita.podkast.service.parser.xml.typeadapter

import com.tickaroo.tikxml.TikXmlConfig
import com.tickaroo.tikxml.XmlReader
import com.tickaroo.tikxml.XmlWriter
import com.tickaroo.tikxml.typeadapter.TypeAdapter
import ismaeldivita.podkast.service.dto.EpisodeDTO
import ismaeldivita.podkast.service.dto.FeedDTO
import ismaeldivita.podkast.service.parser.xml.model.EpisodeXml
import ismaeldivita.podkast.service.parser.xml.model.RssXml
import ismaeldivita.podkast.service.util.DateParser


internal class FeedTypeAdapter : TypeAdapter<FeedDTO> {

    override fun toXml(
        writer: XmlWriter,
        config: TikXmlConfig,
        value: FeedDTO,
        overridingXmlElementTagName: String
    ) = throw NotImplementedError()


    override fun fromXml(reader: XmlReader, config: TikXmlConfig): FeedDTO {
        val adapter = config.getTypeAdapter(RssXml::class.java)
        val rss = adapter.fromXml(reader, config)

        return FeedDTO(
                description = rss.feed.description ?: rss.feed.summary.orEmpty(),
                languageIso639 = rss.feed.language,
                episodes = mapEpisodes(rss.feed.episodesXml)
        )
    }

    private fun mapEpisodes(episodesXml: List<EpisodeXml>): List<EpisodeDTO> =
            episodesXml.map {
                EpisodeDTO(
                        title = it.title,
                        description = it.description ?: it.summary ?: it.subtitle.orEmpty(),
                        audioFileUrl = it.audioFile.url,
                        duration = it.duration,
                        isExplicit = it.explicit,
                        publicationDate = DateParser.parseRFC822(it.pubDate),
                        coverImageUrl = it.coverUrl
                )
            }

}
