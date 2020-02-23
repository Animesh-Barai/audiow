package audiow.user.feature.signin.ui.signin

sealed class SignInAction {

    object StartGoogleSignIn : SignInAction()

    object OnSignIn : SignInAction()
}