package audiow.user.data

import audiow.user.data.interactor.InteractorModule
import audiow.user.data.repository.RepositoryModule
import dagger.Module

@Module(
    includes = [
        RepositoryModule::class,
        InteractorModule::class
    ]
)
abstract class UserDataModule