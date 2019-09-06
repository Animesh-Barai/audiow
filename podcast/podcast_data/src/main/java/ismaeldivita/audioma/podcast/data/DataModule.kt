package ismaeldivita.audioma.podcast.data

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import ismaeldivita.audioma.core.android.LaunchInitializer
import ismaeldivita.audioma.podcast.data.initializer.PodcastDataInitializer
import ismaeldivita.audioma.podcast.data.repository.RepositoryModule
import ismaeldivita.audioma.podcast.data.storage.database.DatabaseModule
import ismaeldivita.audioma.podcast.service.itunes.ItunesServiceModule

@Module(includes = [
    DatabaseModule::class,
    RepositoryModule::class,
    ItunesServiceModule::class
])
abstract class DataModule {

    @Binds
    @IntoSet
    internal abstract fun dataInitializer(initializer: PodcastDataInitializer): LaunchInitializer

}