package ismaeldivita.podkast.service.parser.xml

import com.tickaroo.tikxml.TikXml
import ismaeldivita.podkast.service.model.PodcastDetail
import ismaeldivita.podkast.service.parser.xml.typeadapter.PodcastDetailTypeAdapter

internal object TikXmlProvider {

    val instance: TikXml by lazy {
        TikXml.Builder()
                .exceptionOnUnreadXml(false)
                .addTypeAdapter(PodcastDetail::class.java, PodcastDetailTypeAdapter())
                .build()
    }

}
