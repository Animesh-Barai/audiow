package audiow.user.data.interactor

import dagger.Binds
import dagger.Module

@Module
internal interface InteractorModule {

    @Binds
    fun bindIsUserLoggedIn(isUserLoggedInImpl: IsUserLoggedInImpl): IsUserLoggedIn

    @Binds
    fun bindSignOut(signOut: SignOutImpl): SignOut
}