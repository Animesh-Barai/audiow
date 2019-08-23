package ismaeldivita.audioma.core.util.reactive

import android.app.Application
import android.os.Looper
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import ismaeldivita.audioma.core.android.ApplicationInitializer
import javax.inject.Inject

class ReactiveInitializer @Inject constructor(): ApplicationInitializer {

    override fun initialize(application: Application) {
        val asyncMainThreadScheduler = AndroidSchedulers.from(Looper.getMainLooper(), true)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { asyncMainThreadScheduler }
    }

}