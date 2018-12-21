package ismaeldivita.podkast.data

import dagger.Module
import ismaeldivita.podkast.data.interactor.InteractorModule
import ismaeldivita.podkast.data.repository.RepositoryModule
import ismaeldivita.podkast.data.storage.database.DatabaseModule
import ismaeldivita.podkast.data.storage.preferences.PreferencesModule
import ismaeldivita.podkast.service.ServiceModule

@Module(includes = [
    DatabaseModule::class,
    RepositoryModule::class,
    PreferencesModule::class,
    InteractorModule::class,
    ServiceModule::class
])
class DataModule