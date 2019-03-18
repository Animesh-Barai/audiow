package ismaeldivita.podkast.service.itunes.parser.xml

import com.tickaroo.tikxml.TikXml
import ismaeldivita.podkast.service.itunes.model.ItunesPodcastFeed
import ismaeldivita.podkast.service.itunes.parser.xml.typeadapter.FeedTypeAdapter

internal object TikXmlProvider {

    val instance: TikXml by lazy {
        TikXml.Builder()
                .exceptionOnUnreadXml(false)
                .addTypeAdapter(
                    ItunesPodcastFeed::class.java,
                    FeedTypeAdapter()
                )
                .build()
    }

}
