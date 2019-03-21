package ismaeldivita.noizu.application.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ismaeldivita.noizu.application.PodkastApplication
import ismaeldivita.noizu.core.CoreModule
import ismaeldivita.noizu.podcast.data.DataModule
import ismaeldivita.noizu.ui.screen.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        ApplicationModule::class,
        ViewModelModule::class,
        CoreModule::class,
        DataModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<PodkastApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<PodkastApplication>()

}
