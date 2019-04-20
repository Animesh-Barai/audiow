package ismaeldivita.audioma.application.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ismaeldivita.audioma.application.PodkastApplication
import ismaeldivita.audioma.core.CoreModule
import ismaeldivita.audioma.podcast.PodcastModule
import ismaeldivita.audioma.podcast.data.DataModule
import ismaeldivita.audioma.ui.screen.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        ApplicationModule::class,
        ViewModelModule::class,

        CoreModule::class,

        PodcastModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<PodkastApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<PodkastApplication>()

}
