package ismaeldivita.audioma.app.application.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ismaeldivita.audioma.app.application.AudiomaApplication
import ismaeldivita.audioma.app.screens.BindingModule
import ismaeldivita.audioma.core.CoreModule
import ismaeldivita.audioma.podcast.feature.PodcastModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        CoreModule::class,
        BindingModule::class,
        ApplicationModule::class,
        PodcastModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<AudiomaApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<AudiomaApplication>()

}
