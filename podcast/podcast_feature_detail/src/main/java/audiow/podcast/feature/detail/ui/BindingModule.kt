package audiow.podcast.feature.detail.ui

import audiow.core.android.di.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import audiow.podcast.feature.detail.ui.detail.PodcastDetailFragment
import audiow.podcast.feature.detail.ui.episode.EpisodeFragment

@Module
internal abstract class BindingModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindDetail(): PodcastDetailFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindEpisode(): EpisodeFragment

}