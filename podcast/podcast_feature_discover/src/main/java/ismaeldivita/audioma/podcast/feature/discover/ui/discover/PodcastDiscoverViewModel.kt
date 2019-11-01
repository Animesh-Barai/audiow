package ismaeldivita.audioma.podcast.feature.discover.ui.discover

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.subscribeBy
import ismaeldivita.audioma.core.android.viewmodel.BaseViewModel
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.monitoring.log.Logger
import ismaeldivita.audioma.core.util.reactive.SchedulersProvider
import ismaeldivita.audioma.podcast.data.model.FeedSection
import ismaeldivita.audioma.podcast.feature.discover.ui.discover.feed.FeedAdapter
import javax.inject.Inject

internal class PodcastDiscoverViewModel @Inject constructor(
    private val feedRepository: Repository<FeedSection>,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    val feed = MutableLiveData<List<FeedSection>>()
    val text = MutableLiveData<String>()

    fun init() {
        feedRepository.getAll()
            .subscribeOn(schedulersProvider.io())
            .doOnSuccess { Logger.d(it.toString()) }
            .subscribeBy {
                feed.postValue(it)
                text.postValue(it.toString())
            }
            .registerDisposable()
    }


    fun onAction(action: FeedAdapter.Action){

    }
}