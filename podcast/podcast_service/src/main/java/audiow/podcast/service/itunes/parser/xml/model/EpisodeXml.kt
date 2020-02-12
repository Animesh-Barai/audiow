package audiow.podcast.service.itunes.parser.xml.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml
internal class EpisodeXml {

    @PropertyElement
    lateinit var title: String

    @PropertyElement
    lateinit var pubDate: String

    @Element(name = "enclosure")
    lateinit var audioFile: AudioXml

    @PropertyElement
    var description: String? = null

    @PropertyElement(name = "itunes:duration")
    var duration: String? = null

    @PropertyElement(name = "itunes:explicit")
    var explicit: Boolean = false

    @PropertyElement(name = "itunes:summary")
    var summary: String? = null

    @PropertyElement(name = "itunes:subtitle")
    var subtitle: String? = null

    @Element(name = "itunes:image")
    var image: ImageXml? = null

}
