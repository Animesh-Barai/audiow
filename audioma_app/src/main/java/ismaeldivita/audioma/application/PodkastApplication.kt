package ismaeldivita.audioma.application

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import ismaeldivita.audioma.application.di.DaggerApplicationComponent
import ismaeldivita.audioma.core.android.ApplicationInitializer
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
