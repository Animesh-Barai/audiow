package ismaeldivita.podkast.common.monitoring.log

import android.app.Application
import ismaeldivita.podkast.common.BuildConfig
import ismaeldivita.podkast.common.android.ApplicationInitializer
import ismaeldivita.podkast.common.monitoring.log.timber.LogcatLogger
import timber.log.Timber
import javax.inject.Inject

internal class LoggerInitializer @Inject constructor(): ApplicationInitializer {

    override fun initialize(application: Application) {
        if (BuildConfig.DEBUG) {
            Timber.plant(LogcatLogger())
        }
    }

}