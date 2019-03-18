package ismaeldivita.podkast.service.itunes.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class DateParserTest {

    @Test
    fun testRfc822WithTimeZone() {
        assertEquals(getDate("2018-10-22 00:40:00"),
                DateParser.parseRFC822("22 Oct 2018 01:40 +0100"))
    }

    @Test
    fun testRfc822WithoutSecond() {
        assertEquals(getDate("2018-10-22 01:40:00"),
                DateParser.parseRFC822("Mon, 22 Oct 2018 01:40 +0000"))
    }

    @Test
    fun testRfc822WithoutDayOfWeek() {
        assertEquals(getDate("2018-10-22 01:40:48"),
                DateParser.parseRFC822("22 Oct 2018 01:40:48 GMT"))
    }

    @Test
    fun testRfc822FourDigitYear() {
        assertEquals(getDate("2018-10-22 01:40:48"),
                DateParser.parseRFC822("Mon, 22 Oct 2018 01:40:48 GMT"))
    }

    @Test
    fun testRfc822TwoDigitYear() {
        assertEquals(getDate("2018-10-22 01:40:48"),
                DateParser.parseRFC822("Mon, 22 Oct 18 01:40:48 GMT"))
    }

    @Test
    fun testRfc822WithUtTimeZone() {
        assertEquals(getDate("2018-10-22 01:40:48"),
                DateParser.parseRFC822("Mon, 22 Oct 2018 01:40:48 UT"))
    }

    @Test
    fun testRfc822WithUtcTimeZone() {
        assertEquals(getDate("2018-10-22 01:40:48"),
                DateParser.parseRFC822("Mon, 22 Oct 2018 01:40:48 UTC"))
    }

    @Test
    fun testRfc822WithZTimeZone() {
        assertEquals(getDate("2018-10-22 01:40:48"),
                DateParser.parseRFC822("Mon, 22 Oct 2018 01:40:48 Z"))
    }

    private fun getDate(sDate: String, mTimeZone: TimeZone = TimeZone.getTimeZone("UTC")) =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
                    .apply { timeZone = mTimeZone }
                    .parse(sDate)
}
