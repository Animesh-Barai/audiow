package ismaeldivita.audioma.podcast.feature.detail.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import ismaeldivita.audioma.core.android.ui.ViewModelFragment
import ismaeldivita.audioma.podcast.feature.detail.R
import ismaeldivita.audioma.podcast.feature.detail.databinding.PodcastFeatureDetailFragmentBinding

class PodcastDetailFragment : ViewModelFragment<PodcastDetailViewModel>() {

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

        with(binding) {
            lifecycleOwner = this@PodcastDetailFragment
            imageLoader = Glide.with(this@PodcastDetailFragment)
            vm = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
    }

}