package ismaeldivita.podkast.core.monitoring.log.timber

import timber.log.Timber

internal class LogcatLogger : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement) =
        String.format(
            "[L:%s] [M:%s] [C:%s]",
            element.lineNumber,
            element.methodName,
            super.createStackElementTag(element)
        )

}