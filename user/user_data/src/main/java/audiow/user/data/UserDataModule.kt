package audiow.user.data

import audiow.user.data.interactor.InteractorModule
import dagger.Module

@Module(
    includes = [
        InteractorModule::class
    ]
)
abstract class UserDataModule