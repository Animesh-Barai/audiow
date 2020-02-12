package audiow.podcast.feature.discover.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import audiow.core.android.ui.FragmentTransactor
import audiow.core.android.ui.ViewModelFragment
import audiow.podcast.data.model.Podcast
import audiow.podcast.feature.detail.PodcastDetailFragmentFactory
import audiow.podcast.feature.discover.R
import audiow.podcast.feature.discover.ui.discover.recyclerview.DiscoverAdapter
import javax.inject.Inject
import audiow.podcast.feature.discover.databinding.PodcastFeatureDiscoverFragmentBinding as PodcastFeatureDiscoverFragmentBinding1

internal class PodcastDiscoverFragment : ViewModelFragment<PodcastDiscoverViewModel>(),
    DiscoverAdapter.DiscoverCallback {

    @Inject
    internal lateinit var fragmentTransactor: FragmentTransactor
    @Inject
    internal lateinit var fragmentFactory: PodcastDetailFragmentFactory

    private lateinit var binding: PodcastFeatureDiscoverFragmentBinding1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.podcast_feature_discover_fragment,
            container,
            false
        )
        val adapter = DiscoverAdapter(Glide.with(this), this)

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

    override fun onPodcastSelected(podcast: Podcast) {
        val detailFragment = fragmentFactory.detail(podcast.id)

        fragmentTransactor
            .add(detailFragment)
            .addToBackStack(null)
            .commit()
    }
}