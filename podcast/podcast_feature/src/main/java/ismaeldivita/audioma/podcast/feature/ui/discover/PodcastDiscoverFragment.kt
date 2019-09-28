package ismaeldivita.audioma.podcast.feature.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.bumptech.glide.Glide
import dagger.android.support.DaggerFragment
import io.reactivex.schedulers.Schedulers
import ismaeldivita.audioma.core.android.ui.ViewModelFragment
import ismaeldivita.audioma.core.android.viewmodel.ViewModelFactory
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.monitoring.log.Logger
import ismaeldivita.audioma.podcast.R
import ismaeldivita.audioma.podcast.data.model.FeedSection
import ismaeldivita.audioma.podcast.databinding.PodcastFeatureFragmentDiscoverBinding
import ismaeldivita.audioma.podcast.feature.ui.discover.feed.FeedAdapter
import javax.inject.Inject

internal class PodcastDiscoverFragment : ViewModelFragment<PodcastDiscoverViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<PodcastFeatureFragmentDiscoverBinding>(
            inflater,
            R.layout.podcast_feature_fragment_discover,
            container,
            false
        )

        binding.lifecycleOwner = this
        binding.feed.adapter = FeedAdapter(Glide.with(this)) { viewModel.onAction(it) }
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
    }

}