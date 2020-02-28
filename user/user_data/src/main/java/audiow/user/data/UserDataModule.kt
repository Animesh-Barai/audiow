package audiow.user.data

import audiow.user.data.partner.firebase.FirebaseModule
import audiow.user.data.interactor.InteractorModule
import audiow.user.data.partner.google.GoogleModule
import audiow.user.data.repository.RepositoryModule
import audiow.user.data.storage.database.DatabaseModule
import dagger.Module

@Module(
    includes = [
        RepositoryModule::class,
        InteractorModule::class,
        FirebaseModule::class,
        GoogleModule::class,
        DatabaseModule::class
    ]
)
abstract class UserDataModule