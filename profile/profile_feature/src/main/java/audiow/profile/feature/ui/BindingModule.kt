package audiow.profile.feature.ui

import audiow.core.android.di.PerFragment
import audiow.profile.feature.ui.home.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal interface BindingModule {

    @PerFragment
    @ContributesAndroidInjector
    fun contributeProfilefragment(): ProfileFragment
}