package audiow.application.di

import android.app.Application
import android.content.res.Resources
import audiow.app.R
import dagger.Module
import dagger.Provides
import audiow.application.AudiowApplication
import audiow.core.common.ApplicationProperties
import dagger.Reusable
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideApplication(application: AudiowApplication): Application = application

    @Provides
    @Reusable
    fun provideResources(application: Application): Resources = application.resources

    @Provides
    @Singleton
    fun provideApplicationProperties(resources: Resources): ApplicationProperties {
        val googleWebClientId = resources.getString(R.string.default_web_client_id)

        return ApplicationProperties(
            googleWebClientId = googleWebClientId
        )
    }

}