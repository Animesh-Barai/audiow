package ismaeldivita.audioma.core.monitoring.log

import android.app.Application
import ismaeldivita.audioma.core.BuildConfig
import ismaeldivita.audioma.core.android.ApplicationInitializer
import ismaeldivita.audioma.core.monitoring.log.formatter.JsonLogFormatter
import ismaeldivita.audioma.core.monitoring.log.formatter.SingleLineLogFormatter
import ismaeldivita.audioma.core.monitoring.log.handler.LogcatHandler
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