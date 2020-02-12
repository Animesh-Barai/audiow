package audiow.podcast.service.itunes

import dagger.Module
import dagger.Provides
import audiow.podcast.service.client.ClientFactory
import javax.inject.Singleton

@Module
class ItunesServiceModule {

    @Provides
    @Singleton
    internal fun provideService(clientFactory: ClientFactory): ItunesService =
        ItunesService.build(client = clientFactory.newInstance())

}