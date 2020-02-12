package audiow.application.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import audiow.application.AudiowApplication
import audiow.application.screens.BindingModule
import audiow.core.CoreModule
import audiow.podcast.data.PodcastDataModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        CoreModule::class,
        BindingModule::class,
        ApplicationModule::class,

        PodcastDataModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<AudiowApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<AudiowApplication>()

}
