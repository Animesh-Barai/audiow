package ismaeldivita.audioma.core.util.time

import org.threeten.bp.Instant

object TimeUtils {

    fun getISO8601fromMilli(milli: Long): String = Instant.ofEpochMilli(milli).toString()

    fun getMilliFromISO8601(iso8601: String): Long = Instant.parse(iso8601).toEpochMilli()

}