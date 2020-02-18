package audiow.user.data.repository

import audiow.core.common.SignOutCallback
import audiow.core.data.repository.Repository
import audiow.user.data.model.User
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.multibindings.IntoSet

@Module
interface RepositoryModule {

    @Binds
    @Reusable
    fun bindUserRepository(repository: UserRepository): Repository<User>

    @Binds
    @IntoSet
    fun bindSignOutCallback(callback: UserSignOutCallback): SignOutCallback

}