package audiow.podcast.feature.discover

import androidx.fragment.app.Fragment
import audiow.podcast.feature.discover.ui.discover.PodcastDiscoverFragment
import javax.inject.Inject

class PodcastDiscoverFragmentFactory @Inject constructor() {

    fun discover(): Fragment = PodcastDiscoverFragment()

}