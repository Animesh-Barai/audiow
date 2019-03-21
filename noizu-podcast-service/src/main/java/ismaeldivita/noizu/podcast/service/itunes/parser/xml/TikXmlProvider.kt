package ismaeldivita.noizu.podcast.service.itunes.parser.xml

import com.tickaroo.tikxml.TikXml
import ismaeldivita.noizu.podcast.service.itunes.model.ItunesPodcastFeed
import ismaeldivita.noizu.podcast.service.itunes.parser.xml.typeadapter.FeedTypeAdapter

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
