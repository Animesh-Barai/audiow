package audiow.podcast.service.itunes.parser.xml.model

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Xml

@Xml
internal class AudioXml {

    @Attribute
    var length: String? = null

    @Attribute
    var type: String? = null

    @Attribute
    var url: String? = null

}

