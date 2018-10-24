package ismaeldivita.podkast.service.parser.xml.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "channel")
internal class PodcastXml {

    @PropertyElement
    lateinit var title: String

    @Element
    lateinit var episodesXml: List<EpisodeXml>
}

