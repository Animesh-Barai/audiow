package ismaeldivita.audioma.podcast.feature.detail.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import ismaeldivita.audioma.core.android.ui.FragmentTransactor
import ismaeldivita.audioma.core.android.ui.ViewModelFragment
import ismaeldivita.audioma.core.android.ui.withArgs
import ismaeldivita.audioma.podcast.data.model.Episode
import ismaeldivita.audioma.podcast.feature.detail.PodcastDetailFragmentFactory
import ismaeldivita.audioma.podcast.feature.detail.R
import ismaeldivita.audioma.podcast.feature.detail.databinding.PodcastFeatureDetailFragmentBinding
import ismaeldivita.audioma.podcast.feature.detail.ui.detail.recyclerview.FeedAdapter
import javax.inject.Inject

internal class PodcastDetailFragment : ViewModelFragment<PodcastDetailViewModel>(),
    FeedAdapter.FeedCallback {

    @Inject
    internal lateinit var fragmentTransactor: FragmentTransactor

    @Inject
    lateinit var fragmentFactory: PodcastDetailFragmentFactory

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

        val glide = Glide.with(this@PodcastDetailFragment)

        with(binding) {
            lifecycleOwner = this@PodcastDetailFragment
            episodes.adapter = FeedAdapter(glide, this@PodcastDetailFragment)
            episodes.addItemDecoration(DividerItemDecoration(context, VERTICAL))
            vm = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO handle recreation
        viewModel.init(requireArguments().getLong(ARGUMENT_PODCAST_ID))
    }

    override fun onEpisodeSelected(episode: Episode) {
        val podcastId = requireArguments().getLong(ARGUMENT_PODCAST_ID)

        fragmentTransactor
            .add(fragmentFactory.episode(podcastId, episode.id))
            .addToBackStack(null)
            .commit()
    }
}