package ismaeldivita.audioma.podcast.service.util

import org.junit.Assert.*
import org.junit.Test

class PodcastUtilsTest {

    @Test
    fun testTrimMultiline() {
        val text = "Aaaa bbbbb cccc\n dddd eeee \n  ffff"
        val expected = "Aaaa bbbbb cccc<br/>dddd eeee<br/>ffff"

        assertEquals(expected, PodcastUtils.sanitizeDescription(text))
    }

    @Test
    fun testEncodedCDataTagWithEncodedHtml() {
        val text = """&lt;![CDATA[&lt;p&gt;foo&lt;/p&gt;]]&gt;"""
        val expected = """<p>foo</p>"""

        assertEquals(expected, PodcastUtils.sanitizeDescription(text))
    }

    @Test
    fun testEmpty() {
        val text = ""
        val expected = ""

        assertEquals(expected, PodcastUtils.sanitizeDescription(text))
    }

}