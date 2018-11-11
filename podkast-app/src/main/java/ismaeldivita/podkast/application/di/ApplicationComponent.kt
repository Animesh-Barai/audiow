package ismaeldivita.podkast.application.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ismaeldivita.podkast.application.PodkastApplication

@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            ActivityBindingModule::class
        ]
)
interface ApplicationComponent : AndroidInjector<PodkastApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<PodkastApplication>()

}
