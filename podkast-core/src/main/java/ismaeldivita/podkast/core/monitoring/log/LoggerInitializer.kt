package ismaeldivita.podkast.core.monitoring.log

import android.app.Application
import ismaeldivita.podkast.core.BuildConfig
import ismaeldivita.podkast.core.android.ApplicationInitializer
import ismaeldivita.podkast.core.monitoring.log.timber.LogcatLogger
import timber.log.Timber
import javax.inject.Inject

internal class LoggerInitializer @Inject constructor(): ApplicationInitializer {

    override fun initialize(application: Application) {
        if (BuildConfig.DEBUG) {
            Timber.plant(LogcatLogger())
        }
    }

}