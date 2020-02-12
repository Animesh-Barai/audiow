package audiow.core.util.time

import audiow.core.monitoring.log.Logger
import audiow.core.util.standart.replaceLastOccurrence
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class RFC822DateParser @Inject constructor(
    private val localeProvider: Provider<Locale>
) {

    private val RFC822_MASKS = arrayOf(
        "EEE, dd MMM yy HH:mm:ss z",
        "EEE, dd MMM yy HH:mm z",
        "dd MMM yy HH:mm:ss z",
        "dd MMM yy HH:mm z"
    )

    fun parse(date: String): Date? {
        val sDate = date.trim().convertUnsupportedTimeZones()
        var pp: ParsePosition?
        var parsedDate: Date? = null
        var i = 0

        while (parsedDate == null && i < RFC822_MASKS.size) {
            val df = SimpleDateFormat(RFC822_MASKS[i], localeProvider.get())
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
        if (parsedDate == null) Logger.e("Failed to parse RFC822 to Date", mapOf("value" to date))
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
