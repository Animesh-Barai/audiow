package audiow.user.feature.profile.ui

import audiow.core.android.di.PerFragment
import audiow.user.feature.profile.ui.home.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal interface BindingModule {

    @PerFragment
    @ContributesAndroidInjector
    fun contributeProfilefragment(): ProfileFragment
}