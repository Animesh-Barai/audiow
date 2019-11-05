package ismaeldivita.audioma.app.screens.main

import dagger.Binds
import dagger.Module
import ismaeldivita.audioma.core.android.di.PerActivity
import ismaeldivita.audioma.core.android.ui.FragmentTransactor

@Module
abstract class HomeModule {

    @Binds
    @PerActivity
    abstract fun bindHomeFragmentTransactor(a: HomeFragmentTransactor): FragmentTransactor
}