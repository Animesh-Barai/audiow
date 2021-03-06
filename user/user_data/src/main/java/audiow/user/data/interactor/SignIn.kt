package audiow.user.data.interactor

import audiow.core.data.repository.Repository
import audiow.core.interactor.Interactor
import audiow.core.monitoring.log.Logger
import audiow.core.util.reactive.scheduler.SchedulersProvider
import audiow.core.util.standart.exhaustive
import audiow.user.data.model.SignInMethod
import audiow.user.data.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import com.google.firebase.auth.GoogleAuthProvider

interface SignIn : Interactor<SignInType, Completable>

sealed class SignInType {
    class Google(val account: GoogleSignInAccount) : SignInType()
    object Anonymous : SignInType()
}

internal class SignInImpl @Inject constructor(
    private val userRepository: Repository<User>,
    private val firebaseAuth: FirebaseAuth,
    private val scheduler: SchedulersProvider
) : SignIn {

    override fun invoke(type: SignInType): Completable {
        val handler = when (type) {
            is SignInType.Google -> handleGoogle(type)
            is SignInType.Anonymous -> handleAnonymous(type)
        }.exhaustive

        return handler
            .observeOn(scheduler.io())
            .flatMapCompletable(userRepository::add)
    }

    private fun handleGoogle(type: SignInType.Google): Single<User> =
        Single.create<User> { emitter ->
            val credential = GoogleAuthProvider.getCredential(type.account.idToken, null)

            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { result ->
                    val firebaseUser = firebaseAuth.currentUser

                    if (result.isSuccessful && firebaseUser != null) {
                        val user = User(
                            id = firebaseUser.uid,
                            name = firebaseUser.displayName.orEmpty(),
                            email = firebaseUser.email.orEmpty(),
                            photoUrl = firebaseUser.photoUrl?.toString(),
                            signInMethod = SignInMethod.GOOGLE,
                            isSelected = true
                        )
                        emitter.onSuccess(user)
                    } else {
                        Logger.d("Firebase Google SignIn", mapOf("message" to result.exception))
                        emitter.onError(result.exception ?: Throwable("Firebase Google SignIn"))
                    }
                }
        }

    private fun handleAnonymous(type: SignInType.Anonymous): Single<User> = TODO()
}