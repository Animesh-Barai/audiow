package ismaeldivita.audioma.podcast.feature.detail

import androidx.fragment.app.Fragment
import ismaeldivita.audioma.podcast.feature.detail.ui.detail.PodcastDetailFragment
import javax.inject.Inject

class PodcastDetailFragmentFactory @Inject constructor() {

    fun detail(podcastId: Long): Fragment = PodcastDetailFragment.newInstance(podcastId)

}