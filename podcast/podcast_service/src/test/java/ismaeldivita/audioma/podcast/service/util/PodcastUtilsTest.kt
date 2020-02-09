package ismaeldivita.audioma.podcast.service.util

import org.junit.Assert.*
import org.junit.Test

class PodcastUtilsTest {

    @Test
    fun title_when_not_encoded() {
        val text = "AaAabc d"
        val expected = "AaAabc d"
        assertEquals(expected, PodcastUtils.sanitizeTitle(text))
    }

    @Test
    fun title_when_encoded() {
        val text = "Cafezinho 253 &#8211; Brasiliência 2"
        val expected = "Cafezinho 253 – Brasiliência 2"
        assertEquals(expected, PodcastUtils.sanitizeTitle(text))
    }

    @Test
    fun title_when_empty() {
        val text = ""
        val expected = ""

        assertEquals(expected, PodcastUtils.sanitizeTitle(text))
    }

    @Test
    fun description_when_TrimMultiline() {
        val text = "Aaaa bbbbb cccc\n dddd eeee \n  ffff"
        val expected = "Aaaa bbbbb cccc<br/>dddd eeee<br/>ffff"

        assertEquals(expected, PodcastUtils.sanitizeDescription(text))
    }

    @Test
    fun description_when_contains_encoded_CDATATag_with_encodedHtml() {
        val text = """&lt;![CDATA[&lt;p&gt;foo&lt;/p&gt;]]&gt;"""
        val expected = """<p>foo</p>"""

        assertEquals(expected, PodcastUtils.sanitizeDescription(text))
    }

    @Test
    fun description_when_empty() {
        val text = ""
        val expected = ""

        assertEquals(expected, PodcastUtils.sanitizeDescription(text))
    }
}