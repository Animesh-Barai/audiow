package ismaeldivita.audioma.podcast.feature.discover.ui.discover

import android.os.Bundle
import android.transition.TransitionSet.ORDERING_TOGETHER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.ChangeTransform
import androidx.transition.Fade
import androidx.transition.TransitionSet
import com.bumptech.glide.Glide
import ismaeldivita.audioma.core.android.ui.FragmentTransactor
import ismaeldivita.audioma.core.android.ui.ViewModelFragment
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.feature.detail.PodcastDetailFragmentFactory
import ismaeldivita.audioma.podcast.feature.discover.R
import ismaeldivita.audioma.podcast.feature.discover.ui.discover.recyclerview.DiscoverAdapter
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import ismaeldivita.audioma.podcast.feature.discover.databinding.PodcastFeatureDiscoverFragmentBinding as PodcastFeatureDiscoverFragmentBinding1

internal class PodcastDiscoverFragment : ViewModelFragment<PodcastDiscoverViewModel>(),
    DiscoverAdapter.DiscoverCallback {

    @Inject
    internal lateinit var fragmentTransactor: FragmentTransactor
    @Inject
    internal lateinit var fragment: PodcastDetailFragmentFactory

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

        setupTransition()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition(700, TimeUnit.MILLISECONDS)
        viewModel.init()
    }

    override fun onPodcastSelected(podcast: Podcast, coverView: ImageView) {
        val detailFragment = fragment.detail(podcast.id)

        setupSharedElementTransition(detailFragment)

        fragmentTransactor
            .replace(detailFragment)
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .addSharedElement(coverView, coverView.transitionName)
            .commit()
    }

    private fun setupSharedElementTransition(detailFragment: Fragment) {
        val sharedElementTransition = TransitionSet().apply {
            ordering = ORDERING_TOGETHER
            addTransition(ChangeBounds())
            addTransition(ChangeTransform())
        }

        with(detailFragment) {
            sharedElementEnterTransition = sharedElementTransition
            sharedElementReturnTransition = sharedElementTransition

            enterTransition = Fade().apply {
                startDelay = TRANSITION_DURATION
                duration = TRANSITION_DURATION
            }
            returnTransition = Fade().apply {
                duration = TRANSITION_DURATION
            }
        }
    }

    private fun setupTransition() {
        exitTransition = Fade().apply {
            duration = TRANSITION_DURATION
        }

        reenterTransition = Fade().apply {
            duration = TRANSITION_DURATION
            startDelay = TRANSITION_DURATION
        }
    }

    companion object {
        private const val TRANSITION_DURATION = 220L
    }
}