package audiow.application.screens.launch

sealed class LaunchState {

    object Initialized : LaunchState()

    object Loading : LaunchState()

    object Error : LaunchState()

}