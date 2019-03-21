package ismaeldivita.noizu.application.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ismaeldivita.noizu.MainActivity
import ismaeldivita.noizu.ui.screen.launch.LaunchActivity
import ismaeldivita.noizu.ui.screen.setup.SetupActivity

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun main(): MainActivity

    @ContributesAndroidInjector
    abstract fun launch(): LaunchActivity

    @ContributesAndroidInjector
    abstract fun setup(): SetupActivity

}
