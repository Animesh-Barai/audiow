package audiow.application

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import audiow.application.di.DaggerApplicationComponent
import audiow.core.common.ApplicationInitializer
import javax.inject.Inject

class AudiowApplication : DaggerApplication() {

    @Inject
    lateinit var initializers: Set<@JvmSuppressWildcards ApplicationInitializer>

    override fun onCreate() {
        super.onCreate()
        initializers.forEach { it.initialize(this) }
    }

    override fun applicationInjector(): AndroidInjector<AudiowApplication> =
        DaggerApplicationComponent.builder().create(this)

}
