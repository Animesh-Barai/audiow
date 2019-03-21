package ismaeldivita.audioma.podcast.service.itunes

import dagger.Module
import dagger.Provides
import ismaeldivita.audioma.podcast.service.client.ClientFactory
import javax.inject.Singleton

@Module
class ItunesServiceModule {

    @Provides
    @Singleton
    internal fun provideService(clientFactory: ClientFactory): ItunesService =
        ItunesService.build(client = clientFactory.newInstance())

}