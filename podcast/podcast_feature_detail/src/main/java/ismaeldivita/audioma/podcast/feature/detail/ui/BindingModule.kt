package ismaeldivita.audioma.podcast.feature.detail.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ismaeldivita.audioma.podcast.feature.detail.ui.detail.PodcastDetailFragment

@Module
internal abstract class BindingModule {

    @ContributesAndroidInjector
    abstract fun bindDetail(): PodcastDetailFragment

}