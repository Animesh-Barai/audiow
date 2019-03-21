package ismaeldivita.noizu.core.monitoring.log

import android.app.Application
import ismaeldivita.noizu.core.BuildConfig
import ismaeldivita.noizu.core.android.ApplicationInitializer
import ismaeldivita.noizu.core.monitoring.log.formatter.JsonLogFormatter
import ismaeldivita.noizu.core.monitoring.log.formatter.SingleLineLogFormatter
import ismaeldivita.noizu.core.monitoring.log.handler.LogcatHandler
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