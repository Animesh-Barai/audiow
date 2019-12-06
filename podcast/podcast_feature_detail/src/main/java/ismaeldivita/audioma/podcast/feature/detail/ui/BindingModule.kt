package ismaeldivita.audioma.podcast.feature.detail.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ismaeldivita.audioma.podcast.feature.detail.ui.detail.PodcastDetailFragment
import ismaeldivita.audioma.podcast.feature.detail.ui.episode.EpisodeFragment

@Module
internal abstract class BindingModule {

    @ContributesAndroidInjector
    abstract fun bindDetail(): PodcastDetailFragment

    @ContributesAndroidInjector
    abstract fun bindEpisode(): EpisodeFragment
}