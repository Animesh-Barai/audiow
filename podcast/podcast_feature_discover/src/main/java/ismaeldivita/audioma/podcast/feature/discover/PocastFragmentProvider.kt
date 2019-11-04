package ismaeldivita.audioma.podcast.feature.discover

import androidx.fragment.app.Fragment
import ismaeldivita.audioma.podcast.feature.discover.ui.discover.PodcastDiscoverFragment
import javax.inject.Inject

// TODO create and move to podcast-feature module
class PocastFragmentProvider @Inject constructor() {

    fun discover(): Fragment = PodcastDiscoverFragment()

}