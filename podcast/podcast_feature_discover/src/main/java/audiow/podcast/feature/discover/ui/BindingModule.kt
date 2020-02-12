package audiow.podcast.feature.discover.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import audiow.podcast.feature.discover.ui.discover.PodcastDiscoverFragment

@Module
internal abstract class BindingModule {

    @ContributesAndroidInjector
    abstract fun bindDiscover(): PodcastDiscoverFragment

}