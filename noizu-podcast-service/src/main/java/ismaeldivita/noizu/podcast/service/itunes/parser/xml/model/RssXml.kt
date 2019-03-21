package ismaeldivita.noizu.podcast.service.itunes.parser.xml.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "rss" )
internal class RssXml {

    @Element(name = "channel")
    lateinit var feed: FeedXml

}

