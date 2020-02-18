package audiow.user.data.interactor

import audiow.core.interactor.Interactor
import audiow.user.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Single
import javax.inject.Inject

interface IsUserLoggedIn : Interactor<Unit, Single<Boolean>>

internal class IsUserLoggedInImpl @Inject constructor(
    private val userRepository: UserRepository
) : IsUserLoggedIn {

    override fun invoke(p: Unit): Single<Boolean> {
        val containsFirebaseUser = FirebaseAuth.getInstance().currentUser != null

        return userRepository
            .getAll()
            .map { it.isNotEmpty() && containsFirebaseUser }
    }
}
