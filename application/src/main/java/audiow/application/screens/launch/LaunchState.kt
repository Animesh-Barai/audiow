package audiow.application.screens.launch

sealed class LaunchState {

    sealed class Initialized : LaunchState() {
        object SignIn : Initialized()
        object Home : Initialized()
    }

    object Loading : LaunchState()

    object Error : LaunchState()

}