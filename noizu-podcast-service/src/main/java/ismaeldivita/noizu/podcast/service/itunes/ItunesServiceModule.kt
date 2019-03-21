package ismaeldivita.noizu.podcast.service.itunes

import dagger.Module
import dagger.Provides
import ismaeldivita.noizu.podcast.service.itunes.client.ClientFactory
import javax.inject.Singleton

@Module
class ItunesServiceModule {

    @Provides
    @Singleton
    internal fun provideService(clientFactory: ClientFactory): ItunesService =
        ItunesService.build(client = clientFactory.newInstance())

}