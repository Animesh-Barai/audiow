package ismaeldivita.podkast.service.parser.xml.model

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "item")
internal class EpisodeXml {

    @PropertyElement
    lateinit var title: String
}
