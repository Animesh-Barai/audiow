package ismaeldivita.audioma.podcast.feature.detail.ui.detail

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.subscribeBy
import ismaeldivita.audioma.core.android.viewmodel.BaseViewModel
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.util.reactive.SchedulersProvider
import ismaeldivita.audioma.podcast.data.model.Feed
import ismaeldivita.audioma.podcast.data.model.Podcast
import javax.inject.Inject

class PodcastDetailViewModel @Inject constructor(
    private val feedRepository: Repository<Feed>,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    val podcast = MutableLiveData<Podcast>()

    fun init(podcastId: Long) {
        feedRepository.findById(podcastId)
            .subscribeOn(schedulersProvider.io())
            .subscribeBy {  }
            .registerDisposable()
    }

}