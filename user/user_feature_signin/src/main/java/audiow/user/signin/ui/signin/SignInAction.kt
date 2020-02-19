package audiow.user.signin.ui.signin

sealed class SignInAction {

    object StartGoogleSignIn : SignInAction()

    object OnSignIn : SignInAction()
}