package audiow.core.monitoring.log

import android.app.Application
import audiow.core.BuildConfig
import audiow.core.common.ApplicationInitializer
import audiow.core.monitoring.log.formatter.JsonLogFormatter
import audiow.core.monitoring.log.formatter.SingleLineLogFormatter
import audiow.core.monitoring.log.handler.LogcatHandler
import javax.inject.Inject
import javax.inject.Provider

internal class LogInitializer @Inject constructor(
    private val jsonLogFormatter: Provider<JsonLogFormatter>,
    private val singleLineLogFormatter: Provider<SingleLineLogFormatter>,
    private val logcatHandler: LogcatHandler,
    private val metadataProvider: Provider<Metadata>
) : ApplicationInitializer {

    override fun initialize(application: Application) {
        if (BuildConfig.DEBUG) {
            Logger.initialize(
                metadataProvider = metadataProvider,
                formatter = singleLineLogFormatter.get(),
                handlers = listOf(logcatHandler)
            )
        } else {
            Logger.initialize(
                metadataProvider = metadataProvider,
                formatter = jsonLogFormatter.get(),
                handlers = listOf()
            )
        }
    }

}