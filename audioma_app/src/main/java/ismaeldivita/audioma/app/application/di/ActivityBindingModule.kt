package ismaeldivita.audioma.app.application.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ismaeldivita.audioma.app.launch.LaunchActivity
import ismaeldivita.audioma.app.launch.LaunchModule

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [LaunchModule::class])
    abstract fun bindLaunch(): LaunchActivity

}
