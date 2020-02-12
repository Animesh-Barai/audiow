package audiow.application.screens.main

import dagger.Binds
import dagger.Module
import audiow.core.android.di.PerActivity
import audiow.core.android.ui.FragmentTransactor

@Module
abstract class HomeModule {

    @Binds
    @PerActivity
    abstract fun bindHomeFragmentTransactor(a: HomeFragmentTransactor): FragmentTransactor
}