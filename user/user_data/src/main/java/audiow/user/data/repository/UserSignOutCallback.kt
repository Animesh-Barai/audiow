package audiow.user.data.repository

import audiow.core.common.SignOutCallback
import audiow.core.data.repository.Repository
import audiow.user.data.model.User
import javax.inject.Inject

class UserSignOutCallback @Inject constructor(
    private val userRepository: Repository<User>
) : SignOutCallback {

    override fun onSignOut() = userRepository.clear()
}