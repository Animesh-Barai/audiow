package ismaeldivita.audioma.application.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ismaeldivita.audioma.MainActivity
import ismaeldivita.audioma.ui.screen.launch.LaunchActivity
import ismaeldivita.audioma.ui.screen.setup.SetupActivity

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun main(): MainActivity

    @ContributesAndroidInjector
    abstract fun launch(): LaunchActivity

    @ContributesAndroidInjector
    abstract fun setup(): SetupActivity

}
