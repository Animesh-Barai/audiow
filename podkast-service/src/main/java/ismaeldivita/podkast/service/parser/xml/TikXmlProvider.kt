package ismaeldivita.podkast.service.parser.xml

import com.tickaroo.tikxml.TikXml
import ismaeldivita.podkast.service.model.Feed
import ismaeldivita.podkast.service.parser.xml.typeadapter.FeedTypeAdapter

internal object TikXmlProvider {

    val instance: TikXml by lazy {
        TikXml.Builder()
                .exceptionOnUnreadXml(false)
                .addTypeAdapter(Feed::class.java, FeedTypeAdapter())
                .build()
    }

}
