package ismaeldivita.podkast.core.monitoring.log.timber

import timber.log.Timber
import javax.inject.Inject

class LogcatTree @Inject constructor(): Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String {
        return "(${element.fileName}:${element.lineNumber})#${element.methodName}"
    }

}