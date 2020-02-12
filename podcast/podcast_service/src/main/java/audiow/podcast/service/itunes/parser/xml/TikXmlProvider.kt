package audiow.podcast.service.itunes.parser.xml

import com.tickaroo.tikxml.TikXml
import audiow.podcast.service.itunes.model.ItunesPodcastFeed
import audiow.podcast.service.itunes.parser.xml.typeadapter.RssTypeAdapter

internal object TikXmlProvider {

    val instance: TikXml by lazy {
        TikXml.Builder()
                .exceptionOnUnreadXml(false)
                .addTypeAdapter(
                    ItunesPodcastFeed::class.java,
                    RssTypeAdapter()
                )
                .build()
    }

}
