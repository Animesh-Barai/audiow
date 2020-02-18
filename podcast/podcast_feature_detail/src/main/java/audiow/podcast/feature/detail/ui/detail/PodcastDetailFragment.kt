package audiow.podcast.feature.detail.ui.detail

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.transition.*
import com.bumptech.glide.Glide
import audiow.core.android.ui.fragment.FragmentTransactor
import audiow.core.android.ui.fragment.ViewModelFragment
import audiow.core.android.ui.fragment.withArgs
import audiow.design.transition.SlideExplode
import audiow.podcast.data.model.Episode
import audiow.podcast.feature.detail.PodcastDetailFragmentFactory
import audiow.podcast.feature.detail.R
import audiow.podcast.feature.detail.databinding.PodcastFeatureDetailFragmentBinding
import audiow.podcast.feature.detail.ui.detail.recyclerview.FeedAdapter
import javax.inject.Inject

internal class PodcastDetailFragment : ViewModelFragment<PodcastDetailViewModel>(),
    FeedAdapter.FeedCallback {

    @Inject
    lateinit var fragmentTransactor: FragmentTransactor

    @Inject
    lateinit var fragmentFactory: PodcastDetailFragmentFactory

    private lateinit var binding: PodcastFeatureDetailFragmentBinding
    private val podcastId by lazy { requireArguments().getLong(ARGUMENT_PODCAST_ID) }
    private var currentEpisodeIdTransition: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.podcast_feature_detail_fragment,
            container,
            false
        )

        val glide = Glide.with(this@PodcastDetailFragment)

        with(binding) {
            lifecycleOwner = this@PodcastDetailFragment
            episodes.adapter = FeedAdapter(glide, this@PodcastDetailFragment)
            vm = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(podcastId)
        setupReturnTransition(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_EPISODE_ID, currentEpisodeIdTransition)
    }

    override fun onEpisodeSelected(episode: Episode, view: View) {
        val episodeFragment = fragmentFactory.episode(podcastId, episode.id)

        currentEpisodeIdTransition = episode.id

        setupTransitionForEpisodeFragment(episodeFragment)
        setupExplodeTransition(view)

        fragmentTransactor
            .replace(episodeFragment)
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .addSharedElement(view, view.transitionName)
            .commit()
    }

    /**
     * Setup the episode shared element transition to the EpisodeFragment
     *
     * @param episodeFragment EpisodeFragment instance
     */
    private fun setupTransitionForEpisodeFragment(episodeFragment: Fragment) {
        val sharedElementTransition = TransitionSet()
            .addTransition(ChangeBounds())
            .addTransition(ChangeTransform())
            .addTransition(ChangeImageTransform()).apply {
                duration = TRANSITION_DURATION
                interpolator = FastOutSlowInInterpolator()
                excludeChildren(R.id.scroll, true)
            }

        with(episodeFragment) {
            sharedElementEnterTransition = sharedElementTransition
            sharedElementReturnTransition = sharedElementTransition
        }
    }

    /**
     * Setup the custom explode exist transition for the current fragment
     *
     * @param view view which will be used as the center of the explosion
     */
    private fun setupExplodeTransition(view: View) {
        val viewRect = Rect()

        view.getGlobalVisibleRect(viewRect)

        exitTransition = SlideExplode().apply {
            duration = TRANSITION_DURATION
            interpolator = FastOutSlowInInterpolator()
            epicenterCallback = object : Transition.EpicenterCallback() {
                override fun onGetEpicenter(transition: Transition) = viewRect
            }
        }
    }

    private fun setupReturnTransition(savedInstanceState: Bundle?) {
        val episodeIdTransition = savedInstanceState?.getString(STATE_EPISODE_ID)

        postponeEnterTransition()

        /**
         * The exit transition is lost on recreation. So we need to keep the track of the view
         * used for the explosion in the savedInstance and update the exit transition with the
         * new view coordinates as the screen orientation may have changed
         */

        if (episodeIdTransition != null) {
            (binding.episodes).doOnPreDraw {
                binding.episodes.children
                    .find { v -> v.transitionName == episodeIdTransition }
                    ?.let { v -> setupExplodeTransition(v) }

                startPostponedEnterTransition()
            }
        } else {
            (view?.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

    companion object {
        private const val TRANSITION_DURATION = 300L
        private const val ARGUMENT_PODCAST_ID = "podcast_id"
        private const val STATE_EPISODE_ID = "state_episode_id"

        fun newInstance(podcastId: Long) = PodcastDetailFragment().withArgs {
            putLong(ARGUMENT_PODCAST_ID, podcastId)
        }
    }
}