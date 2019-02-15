package ismaeldivita.podkast.service.parser.xml

import com.tickaroo.tikxml.TikXml
import ismaeldivita.podkast.service.dto.FeedDTO
import ismaeldivita.podkast.service.parser.xml.typeadapter.FeedTypeAdapter

internal object TikXmlProvider {

    val instance: TikXml by lazy {
        TikXml.Builder()
                .exceptionOnUnreadXml(false)
                .addTypeAdapter(FeedDTO::class.java, FeedTypeAdapter())
                .build()
    }

}
