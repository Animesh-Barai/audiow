package ismaeldivita.audioma.app.application.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ismaeldivita.audioma.app.application.AudiomaApplication
import ismaeldivita.audioma.app.screens.BindingModule
import ismaeldivita.audioma.core.CoreModule
import ismaeldivita.audioma.podcast.data.PodcastDataModule
import ismaeldivita.audioma.podcast.feature.detail.PodcastDetailModule
import ismaeldivita.audioma.podcast.feature.discover.PodcastDiscoverModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        CoreModule::class,
        BindingModule::class,
        ApplicationModule::class,

        PodcastDataModule::class,
        PodcastDetailModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<AudiomaApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<AudiomaApplication>()

}
