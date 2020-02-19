package audiow.user.signin.ui.signin

import audiow.core.android.livedata.liveEventOf
import audiow.core.android.viewmodel.BaseViewModel
import audiow.core.util.reactive.scheduler.SchedulersProvider
import audiow.user.data.interactor.SignIn
import audiow.user.data.interactor.SignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val signIn: SignIn,
    private val schedulers: SchedulersProvider
) : BaseViewModel() {

    val action = liveEventOf<SignInAction>()

    fun onSignInClicked() {
        action.postValue(SignInAction.StartGoogleSignIn)
    }

    fun onGoogleSignIn(account: GoogleSignInAccount) {
        signIn(SignInOptions.Google(account))
            .subscribeOn(schedulers.io())
            .subscribeBy(
                onComplete = { action.postValue(SignInAction.OnSignIn) },
                onError = {
                    // TODO display error
                }
            )
            .registerDisposable()
    }
}