package ismaeldivita.podkast.application

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import ismaeldivita.podkast.application.di.DaggerApplicationComponent
import ismaeldivita.podkast.core.android.ApplicationInitializer
import ismaeldivita.podkast.core.monitoring.log.Logger
//import ismaeldivita.podkast.core.monitoring.log.Logger
import javax.inject.Inject

class PodkastApplication : DaggerApplication() {

    @Inject
    lateinit var initializers: Set<@JvmSuppressWildcards ApplicationInitializer>

    override fun onCreate() {
        super.onCreate()
        initializers.forEach { it.initialize(this) }
    }

    override fun applicationInjector(): AndroidInjector<PodkastApplication> =
        DaggerApplicationComponent.builder().create(this)

}
