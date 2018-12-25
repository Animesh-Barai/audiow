package ismaeldivita.podkast.application.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ismaeldivita.podkast.MainActivity
import ismaeldivita.podkast.ui.screen.launch.LaunchActivity
import ismaeldivita.podkast.ui.screen.setup.SetupActivity

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun main(): MainActivity

    @ContributesAndroidInjector
    abstract fun launch(): LaunchActivity

    @ContributesAndroidInjector
    abstract fun setup(): SetupActivity

}
