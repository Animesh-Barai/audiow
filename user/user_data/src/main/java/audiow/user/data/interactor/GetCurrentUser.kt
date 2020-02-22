package audiow.user.data.interactor

import audiow.core.data.repository.Repository
import audiow.core.interactor.Interactor
import audiow.user.data.model.User
import io.reactivex.Single
import javax.inject.Inject

interface GetCurrentUser : Interactor<Unit, Single<User>>

internal class GetCurrentUserImpl @Inject constructor(
    private val userRepository: Repository<User>
) : GetCurrentUser {

    override fun invoke(p: Unit): Single<User> =
        userRepository.getAll()
            .map { it.first() }
}
