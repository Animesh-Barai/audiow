package ismaeldivita.audioma.podcast.feature.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import dagger.android.support.DaggerFragment
import ismaeldivita.audioma.core.interactor.invoke
import ismaeldivita.audioma.podcast.data.interactor.feed.GetFeed
import javax.inject.Inject

class PodcastFeedFragment : DaggerFragment() {

    @Inject
    lateinit var getFeed: GetFeed

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FrameLayout(inflater.context)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFeed().subscribe()
    }
}