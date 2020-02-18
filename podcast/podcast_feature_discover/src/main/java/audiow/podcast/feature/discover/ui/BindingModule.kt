package audiow.podcast.feature.discover.ui

import audiow.core.android.di.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import audiow.podcast.feature.discover.ui.discover.PodcastDiscoverFragment

@Module
internal abstract class BindingModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindDiscover(): PodcastDiscoverFragment

}