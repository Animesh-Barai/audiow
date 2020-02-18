package audiow.podcast.data

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import audiow.core.common.LaunchInitializer
import audiow.podcast.data.initializer.PodcastDataInitializer
import audiow.podcast.data.interactor.InteractorModule
import audiow.podcast.data.repository.RepositoryModule
import audiow.podcast.data.storage.database.DatabaseModule
import audiow.podcast.service.itunes.ItunesServiceModule

@Module(includes = [
    DatabaseModule::class,
    RepositoryModule::class,
    InteractorModule::class,
    ItunesServiceModule::class
])
abstract class PodcastDataModule {

    @Binds
    @IntoSet
    internal abstract fun dataInitializer(initializer: PodcastDataInitializer): LaunchInitializer

}