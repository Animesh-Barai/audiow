package audiow.podcast.service.itunes.parser.xml.typeadapter

import audiow.core.monitoring.log.Logger
import com.tickaroo.tikxml.TikXmlConfig
import com.tickaroo.tikxml.XmlReader
import com.tickaroo.tikxml.XmlWriter
import com.tickaroo.tikxml.typeadapter.TypeAdapter
import audiow.podcast.service.itunes.model.ItunesPodcastEpisode
import audiow.podcast.service.itunes.model.ItunesPodcastFeed
import audiow.podcast.service.itunes.parser.xml.model.EpisodeXml
import audiow.podcast.service.itunes.parser.xml.model.RssXml
import audiow.podcast.service.util.PodcastUtils

internal object RssTypeAdapter : TypeAdapter<ItunesPodcastFeed> {

    private const val ERROR_TAG = "Feed episode parser"

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
            episodesXml.mapNotNull {
                try {
                    ItunesPodcastEpisode(
                            title = PodcastUtils.sanitizeTitle(
                                    requireNotNull(it.title) { "$ERROR_TAG - title is null" }
                            ),

                            subtitle = it.subtitle ?: it.summary,
                            description = PodcastUtils.sanitizeDescription(
                                    it.description ?: it.subtitle ?: it.summary.orEmpty()
                            ),

                            audioFileUrl = requireNotNull(requireNotNull(it.audioFile) {
                                "$ERROR_TAG - audio enclosure is null"
                            }.url) { "$ERROR_TAG - audio url is null" },

                            duration = it.duration,
                            isExplicit = it.explicit,

                            publicationDateRFC822 = requireNotNull(it.pubDate) {
                                "$ERROR_TAG - publicationDateRFC822 is null"
                            },

                            coverImageUrl = it.image?.href
                    )
                } catch (error: Throwable) {
                    Logger.e(error.message ?: ERROR_TAG, mapOf(
                            "title" to it.title,
                            "file" to it.audioFile?.url,
                            "pubDate" to it.pubDate
                    ))
                    null
                }
            }

}
