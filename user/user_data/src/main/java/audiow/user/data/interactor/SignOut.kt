package audiow.user.data.interactor

import audiow.core.common.SignOutCallback
import audiow.core.interactor.Interactor
import audiow.core.util.reactive.GlobalCompositeDisposable
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

interface SignOut : Interactor<Unit, Completable>

internal class SignOutImpl @Inject constructor(
    private val signOutCallbacks: Set<@JvmSuppressWildcards SignOutCallback>,
    @GlobalCompositeDisposable private val globalDisposable: CompositeDisposable,
    private val firebaseAuth: FirebaseAuth
) : SignOut {

    override fun invoke(p: Unit): Completable {
        /** Dispose all global subscriptions */
        globalDisposable.clear()

        return Completable.merge(signOutCallbacks.map { it.onSignOut() })
            .doOnComplete { firebaseAuth.signOut() }
            .onErrorComplete()
    }
}