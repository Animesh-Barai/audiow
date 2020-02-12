package audiow.application.screens

import dagger.Module
import dagger.android.ContributesAndroidInjector
import audiow.application.screens.launch.LaunchActivity
import audiow.application.screens.main.HomeActivity
import audiow.application.screens.main.HomeModule
import audiow.core.android.di.PerActivity
import audiow.podcast.feature.detail.PodcastDetailModule
import audiow.podcast.feature.discover.PodcastDiscoverModule

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
