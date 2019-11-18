package ismaeldivita.audioma.podcast.service.itunes.parser.xml

import com.tickaroo.tikxml.TikXml
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesPodcastFeed
import ismaeldivita.audioma.podcast.service.itunes.parser.xml.typeadapter.RssTypeAdapter

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
