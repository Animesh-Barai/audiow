package audiow.user.data.interactor

import audiow.core.interactor.Interactor
import audiow.core.interactor.invoke
import audiow.core.monitoring.log.Logger
import audiow.user.data.repository.user.UserRepository
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Single
import javax.inject.Inject

interface IsUserLoggedIn : Interactor<Unit, Single<Boolean>>

internal class IsUserLoggedInImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val singOut: SignOut
) : IsUserLoggedIn {

    override fun invoke(p: Unit): Single<Boolean> {
        val containsFirebaseUser = FirebaseAuth.getInstance().currentUser != null

        return userRepository
            .getAll()
            .flatMap {
                val databaseContainsUser = it.isNotEmpty()
                val isLoggedIn = databaseContainsUser && containsFirebaseUser

                /**
                 * The LoggedIn state is the combination of the Database user state and
                 * FirebaseAuth, the condition below detects if for some reason they are not
                 * consistent like database contains user but Firebase not or vice-versa.
                 *
                 * If it's inconsistent let's invoke SignOut and log the error.
                 */
                if (!isLoggedIn && (databaseContainsUser != containsFirebaseUser)) {
                    Logger.e("IsUserLoggedIn inconsistency", mapOf(
                        "database" to databaseContainsUser,
                        "firebase" to containsFirebaseUser
                    ))
                    singOut().andThen(Single.just(false))
                } else {
                    Single.just(isLoggedIn)
                }
            }
    }
}
