package ismaeldivita.podkast.service.parser.xml.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "rss" )
internal class RssXml {

    @Element(name = "channel")
    lateinit var podcastDetail: PodcastDetailXml

}

