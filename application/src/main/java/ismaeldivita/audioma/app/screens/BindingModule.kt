package ismaeldivita.audioma.app.screens

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ismaeldivita.audioma.app.screens.launch.LaunchActivity
import ismaeldivita.audioma.app.screens.main.MainActivity

@Module
abstract class BindingModule {

    @ContributesAndroidInjector
    abstract fun bindLaunch(): LaunchActivity

    @ContributesAndroidInjector
    abstract fun bindMain(): MainActivity
}
