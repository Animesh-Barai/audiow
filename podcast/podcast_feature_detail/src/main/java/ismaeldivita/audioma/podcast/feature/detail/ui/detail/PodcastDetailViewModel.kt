package ismaeldivita.audioma.podcast.feature.detail.ui.detail

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.subscribeBy
import ismaeldivita.audioma.core.android.viewmodel.BaseViewModel
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.monitoring.log.Logger
import ismaeldivita.audioma.core.util.reactive.SchedulersProvider
import ismaeldivita.audioma.podcast.data.model.Podcast
import javax.inject.Inject

class PodcastDetailViewModel @Inject constructor(
    private val podcastRepository: Repository<Podcast>,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    val podcast = MutableLiveData<Podcast>()

    fun init() {
        podcastRepository.getAll()
            .subscribeOn(schedulersProvider.io())
            .doOnSuccess { Logger.d(it.toString()) }
            .subscribeBy { podcast.postValue(it.random()) }
            .registerDisposable()
    }

}