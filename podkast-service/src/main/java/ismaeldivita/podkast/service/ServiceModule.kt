package ismaeldivita.podkast.service

import dagger.Module
import dagger.Provides
import ismaeldivita.podkast.service.client.ClientFactory
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    internal fun provideService(clientFactory: ClientFactory): PodcastService =
        PodcastService.build { client = clientFactory.newInstance() }

}