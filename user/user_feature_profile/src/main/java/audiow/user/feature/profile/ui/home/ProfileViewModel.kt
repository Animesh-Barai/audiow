package audiow.user.feature.profile.ui.home

import audiow.core.android.livedata.liveDataOf
import audiow.core.android.livedata.liveEventOf
import audiow.core.android.viewmodel.BaseViewModel
import audiow.core.interactor.invoke
import audiow.core.util.reactive.scheduler.SchedulersProvider
import audiow.user.data.interactor.GetCurrentUser
import audiow.user.data.interactor.SignOut
import audiow.user.data.model.User
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

internal class ProfileViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUser,
    private val signOut: SignOut,
    private val scheduler: SchedulersProvider
) : BaseViewModel() {

    val user = liveDataOf<User>()
    val action = liveEventOf<ProfileAction>()

    fun init() {
        getCurrentUser()
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.main())
            .subscribeBy(
                onSuccess = { user.value = it }
            )
            .registerDisposable()
    }

    fun onSignOutClicked() {
        signOut()
            .onErrorComplete()
            .subscribeOn(scheduler.io())
            .subscribe { action.postValue(ProfileAction.OnSignOut) }
            .registerDisposable()
    }
}