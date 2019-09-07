package ismaeldivita.audioma.podcast.service.itunes.parser.xml

import com.tickaroo.tikxml.TikXml
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesPodcastDetails
import ismaeldivita.audioma.podcast.service.itunes.parser.xml.typeadapter.DetailsTypeAdapter

internal object TikXmlProvider {

    val instance: TikXml by lazy {
        TikXml.Builder()
                .exceptionOnUnreadXml(false)
                .addTypeAdapter(
                    ItunesPodcastDetails::class.java,
                    DetailsTypeAdapter()
                )
                .build()
    }

}
