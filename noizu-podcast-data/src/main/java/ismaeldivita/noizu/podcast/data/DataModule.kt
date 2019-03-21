package ismaeldivita.noizu.podcast.data

import dagger.Module
import ismaeldivita.noizu.podcast.data.interactor.InteractorModule
import ismaeldivita.noizu.podcast.data.repository.RepositoryModule
import ismaeldivita.noizu.podcast.data.storage.database.DatabaseModule
import ismaeldivita.noizu.podcast.data.storage.preferences.PreferencesModule
import ismaeldivita.noizu.podcast.service.itunes.ItunesServiceModule

@Module(includes = [
    DatabaseModule::class,
    RepositoryModule::class,
    PreferencesModule::class,
    InteractorModule::class,
    ItunesServiceModule::class
])
class DataModule