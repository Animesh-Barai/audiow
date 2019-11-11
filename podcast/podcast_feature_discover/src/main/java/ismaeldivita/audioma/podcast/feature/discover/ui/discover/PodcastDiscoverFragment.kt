package ismaeldivita.audioma.podcast.feature.discover.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import ismaeldivita.audioma.core.android.ui.FragmentTransactor
import ismaeldivita.audioma.core.android.ui.ViewModelFragment
import ismaeldivita.audioma.core.util.standart.exhaustive
import ismaeldivita.audioma.podcast.feature.detail.PodcastDetailFragmentFactory
import ismaeldivita.audioma.podcast.feature.discover.R
import ismaeldivita.audioma.podcast.feature.discover.databinding.PodcastFeatureDiscoverFragmentBinding
import ismaeldivita.audioma.podcast.feature.discover.ui.discover.feed.FeedAdapter
import javax.inject.Inject
import javax.inject.Provider

internal class PodcastDiscoverFragment : ViewModelFragment<PodcastDiscoverViewModel>() {

    @Inject
    internal lateinit var fragmentTransactor: FragmentTransactor
    @Inject
    internal lateinit var detailFactory: PodcastDetailFragmentFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<PodcastFeatureDiscoverFragmentBinding>(
            inflater,
            R.layout.podcast_feature_discover_fragment,
            container,
            false
        )
        val adapter = FeedAdapter(Glide.with(this), ::onAdapterAction)

        with(binding) {
            lifecycleOwner = this@PodcastDiscoverFragment
            feed.adapter = adapter
            vm = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
    }

    private fun onAdapterAction(action: FeedAdapter.Action) {
        when (action) {
            is FeedAdapter.Action.PodcastSelected -> {
                fragmentTransactor.add(detailFactory.detail(action.podcast.id))
            }
            is FeedAdapter.Action.GenreSelected -> TODO()
        }.exhaustive
    }
}