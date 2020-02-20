package audiow.user.data.interactor

import audiow.core.interactor.Interactor
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.reactivex.Completable
import javax.inject.Inject

interface SignIn : Interactor<SignInOptions, Completable>

sealed class SignInOptions {
    class Google(val account: GoogleSignInAccount) : SignInOptions()
}

internal class SignInImpl @Inject constructor() : SignIn {

    // TODO Firebase Auth
    override fun invoke(options: SignInOptions): Completable = Completable.complete()

}