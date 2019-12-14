package ismaeldivita.audioma.podcast.feature.detail.ui.episode

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import ismaeldivita.audioma.core.android.ui.ViewModelFragment
import ismaeldivita.audioma.core.android.ui.withArgs
import ismaeldivita.audioma.podcast.feature.detail.R
import ismaeldivita.audioma.podcast.feature.detail.databinding.PodcastFeatureDetailEpisodeFragmentBinding

internal class EpisodeFragment : ViewModelFragment<EpisodeViewModel>() {

    private lateinit var binding: PodcastFeatureDetailEpisodeFragmentBinding

    private val podcastId by lazy { requireArguments().getLong(ARGUMENT_PODCAST_ID) }
    private val episodeId by lazy { requireArguments().getString(ARGUMENT_EPISODE_ID) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.podcast_feature_detail_episode_fragment,
            container,
            false
        )

        with(binding) {
            lifecycleOwner = this@EpisodeFragment
            scroll.transitionName = episodeId
            imageLoader = Glide.with(this@EpisodeFragment)
            vm = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(podcastId, episodeId)
    }

    companion object {
        private const val ARGUMENT_PODCAST_ID = "podcast_id"
        private const val ARGUMENT_EPISODE_ID = "episode_id"

        fun newInstance(podcastId: Long, episodeId: String) = EpisodeFragment().withArgs {
            putLong(ARGUMENT_PODCAST_ID, podcastId)
            putString(ARGUMENT_EPISODE_ID, episodeId)
        }
    }
}