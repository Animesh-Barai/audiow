package audiow.podcast.feature.detail

import androidx.fragment.app.Fragment
import audiow.podcast.feature.detail.ui.detail.PodcastDetailFragment
import audiow.podcast.feature.detail.ui.episode.EpisodeFragment
import javax.inject.Inject

class PodcastDetailFragmentFactory @Inject constructor() {

    fun detail(podcastId: Long): Fragment = PodcastDetailFragment.newInstance(podcastId)

    fun episode(podcastId: Long, episodeId: String): Fragment =
        EpisodeFragment.newInstance(podcastId, episodeId)

}