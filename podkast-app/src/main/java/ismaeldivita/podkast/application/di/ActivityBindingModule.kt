package ismaeldivita.podkast.application.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ismaeldivita.podkast.MainActivity

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun main(): MainActivity

}
