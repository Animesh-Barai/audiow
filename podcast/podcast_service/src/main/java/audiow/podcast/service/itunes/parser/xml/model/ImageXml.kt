package audiow.podcast.service.itunes.parser.xml.model

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Xml

@Xml
internal class ImageXml {

    @Attribute
    var href: String? = null

}
