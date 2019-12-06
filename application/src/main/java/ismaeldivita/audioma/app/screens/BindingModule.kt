package ismaeldivita.audioma.app.screens

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ismaeldivita.audioma.app.screens.launch.LaunchActivity
import ismaeldivita.audioma.app.screens.main.HomeActivity
import ismaeldivita.audioma.app.screens.main.HomeModule
import ismaeldivita.audioma.core.android.di.PerActivity
import ismaeldivita.audioma.podcast.feature.detail.PodcastDetailModule
import ismaeldivita.audioma.podcast.feature.discover.PodcastDiscoverModule

@Module
abstract class BindingModule {

    @ContributesAndroidInjector
    abstract fun bindLaunch(): LaunchActivity

    @PerActivity
    @ContributesAndroidInjector(
        modules = [
            HomeModule::class,
            PodcastDiscoverModule::class,
            PodcastDetailModule::class
        ]
    )
    abstract fun bindMain(): HomeActivity
}
