package ismaeldivita.audioma.podcast.data

import dagger.Module
import ismaeldivita.audioma.podcast.data.interactor.InteractorModule
import ismaeldivita.audioma.podcast.data.repository.RepositoryModule
import ismaeldivita.audioma.podcast.data.storage.database.DatabaseModule
import ismaeldivita.audioma.podcast.data.storage.preferences.PreferencesModule
import ismaeldivita.audioma.podcast.service.itunes.ItunesServiceModule

@Module(includes = [
    DatabaseModule::class,
    RepositoryModule::class,
    PreferencesModule::class,
    InteractorModule::class,
    ItunesServiceModule::class
])
class DataModule