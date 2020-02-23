package audiow.user.feature.signin.ui

import audiow.core.android.di.PerActivity
import audiow.user.feature.signin.ui.signin.SignInActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingModule {

    @PerActivity
    @ContributesAndroidInjector
    abstract fun bindDetail(): SignInActivity

}