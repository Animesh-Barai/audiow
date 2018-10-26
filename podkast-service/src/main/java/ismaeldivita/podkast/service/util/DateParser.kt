package ismaeldivita.podkast.service.util

import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

object DateParser {

    private val RFC822_MASKS = arrayOf(
            "EEE, dd MMM yy HH:mm:ss z",
            "EEE, dd MMM yy HH:mm z",
            "dd MMM yy HH:mm:ss z",
            "dd MMM yy HH:mm z"
    )

    fun parseRFC822(date: String, locale: Locale = Locale.US): Date? {
        val sDate = date.trim().convertUnsupportedTimeZones()
        var pp: ParsePosition?
        var parsedDate: Date? = null
        var i = 0

        while (parsedDate == null && i < RFC822_MASKS.size) {
            val df = SimpleDateFormat(RFC822_MASKS[i], locale)
            try {
                pp = ParsePosition(0)
                parsedDate = df.parse(sDate, pp)
                if (pp.index != sDate.length) {
                    parsedDate = null
                }
            } catch (ex1: Exception) {
            }
            i++
        }
        // TODO Log error events when date is null
        return parsedDate
    }

    private fun String.convertUnsupportedTimeZones(): String {
        val unsupportedTimeZones = listOf("UT", "Z")
        unsupportedTimeZones.forEach {
            if (endsWith(it)) {
                return replaceLastOccurrence(it, "UTC")
            }
        }
        return this
    }

}
