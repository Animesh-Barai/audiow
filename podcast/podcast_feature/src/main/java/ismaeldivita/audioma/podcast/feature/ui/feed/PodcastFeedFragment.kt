package ismaeldivita.audioma.podcast.feature.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import dagger.android.support.DaggerFragment
import io.reactivex.schedulers.Schedulers
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.monitoring.log.Logger
import ismaeldivita.audioma.podcast.data.model.FeedSection
import javax.inject.Inject

class PodcastFeedFragment : DaggerFragment() {

    @Inject
    lateinit var repository: Repository<FeedSection>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FrameLayout(inflater.context)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository
            .getAll()
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                Logger.i(it.toString())
            }
            .doOnError {
                Logger.i(it.toString())
            }
            .subscribe()
    }
}