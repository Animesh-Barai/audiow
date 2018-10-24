package ismaeldivita.podkast.service.parser.xml.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml
internal class PodcastXml {

    @PropertyElement
    lateinit var title: String

    @PropertyElement
    var description: String? = null

    @PropertyElement
    var summary: String? = null

    @Element(name = "item")
    lateinit var episodesXml: List<EpisodeXml>
}

