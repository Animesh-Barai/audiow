package audiow.user.data.interactor

import audiow.core.common.SignOutCallback
import audiow.core.interactor.Interactor
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

interface SignOut : Interactor<Unit, Completable>

internal class SignOutImpl @Inject constructor(
    private val signOutCallbacks: Set<SignOutCallback>,
    private val globalDisposable: CompositeDisposable
) : SignOut {

    override fun invoke(p: Unit): Completable =
        Completable.merge(signOutCallbacks.map { it.onSignOut() })
            .doOnSubscribe {
                globalDisposable.clear()
                FirebaseAuth.getInstance().signOut()
            }
            .onErrorComplete()
}