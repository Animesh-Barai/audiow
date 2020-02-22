package audiow.user.data.interactor

import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module
internal interface InteractorModule {

    @Binds
    @Reusable
    fun bindIsUserLoggedIn(isUserLoggedInImpl: IsUserLoggedInImpl): IsUserLoggedIn

    @Binds
    @Reusable
    fun bindSignIn(signIn: SignInImpl): SignIn

    @Binds
    @Reusable
    fun bindSignOut(signOut: SignOutImpl): SignOut

    @Binds
    @Reusable
    fun bindGetCurrentUser(getCurrentUser: GetCurrentUserImpl): GetCurrentUser
}