package ismaeldivita.audioma.podcast.service.itunes.parser.xml

import com.tickaroo.tikxml.TikXml
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesPodcastRss
import ismaeldivita.audioma.podcast.service.itunes.parser.xml.typeadapter.RssTypeAdapter

internal object TikXmlProvider {

    val instance: TikXml by lazy {
        TikXml.Builder()
                .exceptionOnUnreadXml(false)
                .addTypeAdapter(
                    ItunesPodcastRss::class.java,
                    RssTypeAdapter()
                )
                .build()
    }

}
