package audiow.podcast.feature.subscriptions.ui

import audiow.core.android.di.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import audiow.podcast.feature.subscriptions.ui.subscriptions.PodcastSubscriptionsFragment

@Module
internal abstract class BindingModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun bindDiscover(): PodcastSubscriptionsFragment

}