package audiow.podcast.feature.discover.ui.discover

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.subscribeBy
import audiow.core.android.viewmodel.BaseViewModel
import audiow.core.data.repository.Repository
import audiow.core.util.reactive.scheduler.SchedulersProvider
import audiow.podcast.data.model.Discover
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class PodcastDiscoverViewModel @Inject constructor(
    private val feedRepository: Repository<Discover>,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    val feed = MutableLiveData<List<Discover>>()

    fun init() {
        if (feed.value.isNullOrEmpty()) {
            feedRepository.getAll()
                .subscribeOn(schedulersProvider.io())
                .delay(500, TimeUnit.MILLISECONDS, schedulersProvider.computation())
                .doOnSuccess { }
                .subscribeBy { feed.postValue(it) }
                .registerDisposable()
        }
    }
}