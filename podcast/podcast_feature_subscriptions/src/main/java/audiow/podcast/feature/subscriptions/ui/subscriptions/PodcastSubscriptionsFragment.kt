package audiow.podcast.feature.subscriptions.ui.subscriptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import audiow.core.android.ui.fragment.FragmentTransactor
import audiow.core.android.ui.fragment.ViewModelFragment
import audiow.podcast.data.model.Podcast
import audiow.podcast.feature.detail.PodcastDetailFragmentFactory
import audiow.podcast.feature.subscriptions.R
import audiow.podcast.feature.subscriptions.databinding.PodcastFeatureSubscriptionsFragmentBinding
import audiow.podcast.feature.subscriptions.ui.subscriptions.recyclerview.SubscriptionAdapter
import com.bumptech.glide.Glide
import javax.inject.Inject

internal class PodcastSubscriptionsFragment : ViewModelFragment<PodcastSubscriptionsViewModel>(),
    SubscriptionAdapter.SubscriptionsCallback {

    @Inject
    internal lateinit var fragmentTransactor: FragmentTransactor
    @Inject
    internal lateinit var detailFactory: PodcastDetailFragmentFactory

    private lateinit var binding: PodcastFeatureSubscriptionsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.podcast_feature_subscriptions_fragment,
            container,
            false
        )
        val adapter = SubscriptionAdapter(Glide.with(this), this)

        with(binding) {
            lifecycleOwner = this@PodcastSubscriptionsFragment
            subscriptions.adapter = adapter
            vm = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
    }

    override fun onPodcastSelected(podcast: Podcast) {
        val detailFragment = detailFactory.detail(podcast.id)

        fragmentTransactor
            .add(detailFragment)
            .addToBackStack(null)
            .commit()
    }
}