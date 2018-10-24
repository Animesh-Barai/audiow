package ismaeldivita.podkast.service.parser.xml

import com.tickaroo.tikxml.TikXml
import ismaeldivita.podkast.service.model.Podcast
import ismaeldivita.podkast.service.parser.xml.typeadapter.PodcastTypeAdapter

object TikXmlProvider {

    val instance: TikXml by lazy {
        TikXml.Builder()
                .exceptionOnUnreadXml(false)
                .addTypeAdapter(Podcast::class.java, PodcastTypeAdapter())
                .build()
    }

}
