package ismaeldivita.audioma.podcast.service.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class DateParserTest {

    @Test
    fun rfc822_with_TimeZone() {
        assertEquals(getDate("2018-10-22 00:40:00"),
                DateParser.parseRFC822("22 Oct 2018 01:40 +0100"))
    }

    @Test
    fun rfc822_without_second() {
        assertEquals(getDate("2018-10-22 01:40:00"),
                DateParser.parseRFC822("Mon, 22 Oct 2018 01:40 +0000"))
    }

    @Test
    fun rfc822_without_day_of_week() {
        assertEquals(getDate("2018-10-22 01:40:48"),
                DateParser.parseRFC822("22 Oct 2018 01:40:48 GMT"))
    }

    @Test
    fun rfc822_four_digit_year() {
        assertEquals(getDate("2018-10-22 01:40:48"),
                DateParser.parseRFC822("Mon, 22 Oct 2018 01:40:48 GMT"))
    }

    @Test
    fun rfc822_two_digit_year() {
        assertEquals(getDate("2018-10-22 01:40:48"),
                DateParser.parseRFC822("Mon, 22 Oct 18 01:40:48 GMT"))
    }

    @Test
    fun rfc822_with_UT_TimeZone() {
        assertEquals(getDate("2018-10-22 01:40:48"),
                DateParser.parseRFC822("Mon, 22 Oct 2018 01:40:48 UT"))
    }

    @Test
    fun rfc822_with_UTC_TimeZone() {
        assertEquals(getDate("2018-10-22 01:40:48"),
                DateParser.parseRFC822("Mon, 22 Oct 2018 01:40:48 UTC"))
    }

    @Test
    fun rfc822_with_Z_TimeZone() {
        assertEquals(getDate("2018-10-22 01:40:48"),
                DateParser.parseRFC822("Mon, 22 Oct 2018 01:40:48 Z"))
    }

    private fun getDate(sDate: String, mTimeZone: TimeZone = TimeZone.getTimeZone("UTC")) =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
                    .apply { timeZone = mTimeZone }
                    .parse(sDate)
}
