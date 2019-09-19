package ismaeldivita.audioma.podcast.feature.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ismaeldivita.audioma.podcast.feature.ui.discover.PodcastDiscoverFragment

@Module
internal abstract class BindingModule {

    @ContributesAndroidInjector
    abstract fun bindDiscover(): PodcastDiscoverFragment

}