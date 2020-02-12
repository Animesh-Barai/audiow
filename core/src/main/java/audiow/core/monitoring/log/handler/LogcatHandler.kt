package audiow.core.monitoring.log.handler

import android.util.Log
import audiow.core.monitoring.log.Logger
import audiow.core.monitoring.log.Logger.Level.*
import audiow.core.util.standart.exhaustive
import javax.inject.Inject

internal class LogcatHandler @Inject constructor(): Logger.Handler {

    private val tag = "LogcatHandler"

    override fun log(message: String, level: Logger.Level) {
        when (level) {
            DEBUG -> Log.d(tag, message)
            INFO -> Log.i(tag, message)
            WARN -> Log.w(tag, message)
            ERROR -> Log.e(tag, message)
        }.exhaustive
    }

}