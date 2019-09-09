package ismaeldivita.audioma.podcast.feature.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ismaeldivita.audioma.podcast.feature.ui.feed.PodcastFeedFragment

@Module
internal interface BindingModule {

    @ContributesAndroidInjector
    fun feedFragment(): PodcastFeedFragment

}