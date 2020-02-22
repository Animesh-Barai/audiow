package audiow.user.data

import audiow.user.data.firebase.FirebaseModule
import audiow.user.data.interactor.InteractorModule
import audiow.user.data.repository.RepositoryModule
import audiow.user.data.storage.DatabaseModule
import dagger.Module

@Module(
    includes = [
        RepositoryModule::class,
        InteractorModule::class,
        FirebaseModule::class,
        DatabaseModule::class
    ]
)
abstract class UserDataModule