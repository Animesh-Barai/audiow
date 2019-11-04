package ismaeldivita.audioma.app.screens

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ismaeldivita.audioma.app.screens.launch.LaunchActivity
import ismaeldivita.audioma.app.screens.main.MainActivity
import ismaeldivita.audioma.app.screens.main.MainModule
import ismaeldivita.audioma.core.android.di.PerActivity
import ismaeldivita.audioma.podcast.feature.discover.PodcastDiscoverModule

@Module
abstract class BindingModule {

    @ContributesAndroidInjector
    abstract fun bindLaunch(): LaunchActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [MainModule::class, PodcastDiscoverModule::class])
    abstract fun bindMain(): MainActivity
}
