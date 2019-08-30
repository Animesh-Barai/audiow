package ismaeldivita.audioma.app.application.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ismaeldivita.audioma.app.screens.launch.LaunchActivity
import ismaeldivita.audioma.app.screens.launch.LaunchModule
import ismaeldivita.audioma.app.screens.main.PlayerActivity

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [LaunchModule::class])
    abstract fun bindLaunch(): LaunchActivity

    @ContributesAndroidInjector
    abstract fun bindMain(): PlayerActivity
}
