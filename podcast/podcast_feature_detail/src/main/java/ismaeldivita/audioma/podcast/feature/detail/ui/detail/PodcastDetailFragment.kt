package ismaeldivita.audioma.podcast.feature.detail.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import ismaeldivita.audioma.core.android.ui.ViewModelFragment
import ismaeldivita.audioma.core.android.ui.withArgs
import ismaeldivita.audioma.podcast.feature.detail.R
import ismaeldivita.audioma.podcast.feature.detail.databinding.PodcastFeatureDetailFragmentBinding
import ismaeldivita.audioma.podcast.feature.detail.ui.detail.recyclerview.FeedAdapter

internal class PodcastDetailFragment : ViewModelFragment<PodcastDetailViewModel>() {

    companion object {
        private const val ARGUMENT_PODCAST_ID = "podcast_id"

        fun newInstance(podcastId: Long) = PodcastDetailFragment().withArgs {
            putLong(ARGUMENT_PODCAST_ID, podcastId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<PodcastFeatureDetailFragmentBinding>(
            inflater,
            R.layout.podcast_feature_detail_fragment,
            container,
            false
        )
        val adapter = FeedAdapter()

        with(binding) {
            lifecycleOwner = this@PodcastDetailFragment
            imageLoader = Glide.with(this@PodcastDetailFragment)
            episodes.adapter = adapter
            vm = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(requireArguments().getLong(ARGUMENT_PODCAST_ID))
    }

}