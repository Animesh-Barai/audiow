package ismaeldivita.podkast.core.monitoring.log

import android.app.Application
import ismaeldivita.podkast.core.BuildConfig
import ismaeldivita.podkast.core.android.ApplicationInitializer
import ismaeldivita.podkast.core.monitoring.log.timber.LogcatTree
import timber.log.Timber
import javax.inject.Inject

internal class LogInitializer @Inject constructor(
    private val logcatTree: LogcatTree
): ApplicationInitializer {

    override fun initialize(application: Application) {
        if (BuildConfig.DEBUG) {
            Timber.plant(logcatTree)
        }
    }

}