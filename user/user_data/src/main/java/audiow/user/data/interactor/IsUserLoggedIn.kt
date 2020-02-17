package audiow.user.data.interactor

import audiow.core.interactor.Interactor
import audiow.user.data.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

interface IsUserLoggedIn : Interactor<Unit, Single<Boolean>>

internal class IsUserLoggedInImpl @Inject constructor(
    private val userRepository: UserRepository
) : IsUserLoggedIn {

    /**
     *  For now just check if the User repository is not empty.
     *  TODO Add Firebase check
     */
    override fun invoke(p: Unit): Single<Boolean> =
        userRepository
            .getAll()
            .map { it.isNotEmpty() }
}
