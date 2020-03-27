package audiow.podcast.feature.subscriptions.ui.subscriptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import audiow.core.android.ui.fragment.FragmentTransactor
import audiow.core.android.ui.fragment.ViewModelFragment
import audiow.podcast.feature.detail.PodcastDetailFragmentFactory
import audiow.podcast.feature.subscriptions.R
import audiow.podcast.feature.subscriptions.databinding.PodcastFeatureSubscriptionsFragmentBinding
import javax.inject.Inject

internal class PodcastSubscriptionsFragment : ViewModelFragment<PodcastSubscriptionsViewModel>() {

    @Inject
    internal lateinit var fragmentTransactor: FragmentTransactor
    @Inject
    internal lateinit var fragmentFactory: PodcastDetailFragmentFactory

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

        with(binding) {
            lifecycleOwner = this@PodcastSubscriptionsFragment
            vm = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
    }
}