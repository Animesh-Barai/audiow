package ismaeldivita.audioma.app.launch

sealed class LaunchState {

    object Initialized : LaunchState()

    object Loading : LaunchState()

    object Error : LaunchState()

}