package audiow.application.di

import android.app.Application
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import audiow.application.AudiowApplication
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideApplication(application: AudiowApplication): Application = application

    @Provides
    @Singleton
    fun provideResources(application: Application): Resources = application.resources

}