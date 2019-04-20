package ismaeldivita.audioma.podcast.service.itunes.parser.xml.model

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Xml

@Xml
internal class AudioXml {

    @Attribute
    lateinit var length: String

    @Attribute
    lateinit var type: String

    @Attribute
    lateinit var url: String

}

