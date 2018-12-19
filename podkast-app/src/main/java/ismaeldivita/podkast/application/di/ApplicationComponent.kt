package ismaeldivita.podkast.application.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ismaeldivita.podkast.application.PodkastApplication
import ismaeldivita.podkast.data.DataModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            ActivityBindingModule::class,
            ApplicationModule::class,
            DataModule::class
        ]
)
interface ApplicationComponent : AndroidInjector<PodkastApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<PodkastApplication>()

}
