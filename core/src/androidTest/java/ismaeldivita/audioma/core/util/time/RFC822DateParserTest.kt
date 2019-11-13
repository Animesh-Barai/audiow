package ismaeldivita.audioma.core.util.time

import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Provider

class RFC822DateParserTest {
    
    private val dateParser = RFC822DateParser(Provider { Locale.US })

    @Test
    fun rfc822_with_TimeZone() {
        assertEquals(
            getDate("2018-10-22 00:40:00"),
            dateParser.parse("22 Oct 2018 01:40 +0100")
        )
    }

    @Test
    fun rfc822_without_second() {
        assertEquals(
            getDate("2018-10-22 01:40:00"),
            dateParser.parse("Mon, 22 Oct 2018 01:40 +0000")
        )
    }

    @Test
    fun rfc822_without_day_of_week() {
        assertEquals(
            getDate("2018-10-22 01:40:48"),
            dateParser.parse("22 Oct 2018 01:40:48 GMT")
        )
    }

    @Test
    fun rfc822_four_digit_year() {
        assertEquals(
            getDate("2018-10-22 01:40:48"),
            dateParser.parse("Mon, 22 Oct 2018 01:40:48 GMT")
        )
    }

    @Test
    fun rfc822_two_digit_year() {
        assertEquals(
            getDate("2018-10-22 01:40:48"),
            dateParser.parse("Mon, 22 Oct 18 01:40:48 GMT")
        )
    }

    @Test
    fun rfc822_with_UT_TimeZone() {
        assertEquals(
            getDate("2018-10-22 01:40:48"),
            dateParser.parse("Mon, 22 Oct 2018 01:40:48 UT")
        )
    }

    @Test
    fun rfc822_with_UTC_TimeZone() {
        assertEquals(
            getDate("2018-10-22 01:40:48"),
            dateParser.parse("Mon, 22 Oct 2018 01:40:48 UTC")
        )
    }

    @Test
    fun rfc822_with_Z_TimeZone() {
        assertEquals(
            getDate("2018-10-22 01:40:48"),
            dateParser.parse("Mon, 22 Oct 2018 01:40:48 Z")
        )
    }

    private fun getDate(sDate: String, mTimeZone: TimeZone = TimeZone.getTimeZone("UTC")) =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            .apply { timeZone = mTimeZone }
            .parse(sDate)
}