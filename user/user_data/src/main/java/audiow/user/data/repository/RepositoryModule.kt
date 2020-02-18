package audiow.user.data.repository

import audiow.core.data.repository.Repository
import audiow.user.data.model.User
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindUserRepository(repository: UserRepository): Repository<User>

}