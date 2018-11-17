package ismaeldivita.podkast.service.parser.xml.typeadapter

import com.tickaroo.tikxml.TikXmlConfig
import com.tickaroo.tikxml.XmlReader
import com.tickaroo.tikxml.XmlWriter
import com.tickaroo.tikxml.typeadapter.TypeAdapter
import ismaeldivita.podkast.service.model.Episode
import ismaeldivita.podkast.service.model.PodcastDetail
import ismaeldivita.podkast.service.parser.xml.model.EpisodeXml
import ismaeldivita.podkast.service.parser.xml.model.RssXml
import ismaeldivita.podkast.service.util.DateParser


internal class PodcastDetailTypeAdapter : TypeAdapter<PodcastDetail> {

    override fun toXml(
            writer: XmlWriter,
            config: TikXmlConfig,
            value: PodcastDetail,
            overridingXmlElementTagName: String
    ) = throw NotImplementedError()


    override fun fromXml(reader: XmlReader, config: TikXmlConfig): PodcastDetail {
        val adapter = config.getTypeAdapter(RssXml::class.java)
        val rss = adapter.fromXml(reader, config)

        return PodcastDetail(
                description = rss.podcastDetail.description ?: rss.podcastDetail.summary.orEmpty(),
                languageIso639 = rss.podcastDetail.language,
                episodes = mapEpisodes(rss.podcastDetail.episodesXml)
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
                        publicationDate = DateParser.parseRFC822(it.pubDate),
                        coverImageUrl = it.coverUrl
                )
            }

}
