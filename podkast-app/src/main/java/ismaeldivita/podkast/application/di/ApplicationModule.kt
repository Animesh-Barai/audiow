package ismaeldivita.podkast.application.di

import android.app.Application
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import ismaeldivita.podkast.application.PodkastApplication
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideApplication(application: PodkastApplication): Application = application

    @Provides
    @Singleton
    fun provideResources(application: Application): Resources = application.resources

}