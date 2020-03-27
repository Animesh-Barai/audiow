package audiow.podcast.feature.subscriptions

import androidx.fragment.app.Fragment
import audiow.podcast.feature.subscriptions.ui.subscriptions.PodcastSubscriptionsFragment
import javax.inject.Inject

class PodcastSubscriptionsFragmentFactory @Inject constructor() {

    fun subscriptions(): Fragment = PodcastSubscriptionsFragment()
}