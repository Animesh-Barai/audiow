package ismaeldivita.podkast.service

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideService(): PodcastService = PodcastService.build {}

}