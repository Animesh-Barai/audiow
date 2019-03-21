package ismaeldivita.noizu.core.monitoring.log

import ismaeldivita.noizu.core.util.time.TimeProvider
import ismaeldivita.noizu.core.util.time.TimeUtils
import javax.inject.Inject

internal class Metadata @Inject constructor(timeProvider: TimeProvider) {

    val created: String = TimeUtils.getISO8601fromMilli(timeProvider.getCurrentTimeMillis())

    val origin: String = run {
        val element = Throwable().stackTrace[6]
        "(${element.fileName}:${element.lineNumber})#${element.methodName}"
    }

}