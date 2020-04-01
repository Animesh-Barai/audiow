package audiow.podcast.feature.subscriptions.ui.subscriptions

import androidx.lifecycle.MutableLiveData
import audiow.core.android.viewmodel.BaseViewModel
import audiow.core.data.repository.Repository
import audiow.core.data.repository.RepositoryWatcher
import audiow.core.monitoring.log.Logger
import audiow.core.util.reactive.scheduler.SchedulersProvider
import audiow.podcast.data.model.Podcast
import audiow.user.data.model.Subscription
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

internal class PodcastSubscriptionsViewModel @Inject constructor(
    private val subscriptionWatcher: RepositoryWatcher<Subscription>,
    private val podcastRepository: Repository<Podcast>,
    private val schedulers: SchedulersProvider
) : BaseViewModel() {

    val subscriptions = MutableLiveData<List<Podcast>>()

    fun init() {
        subscriptionWatcher.onChanged()
            .switchMapSingle { subscriptions ->
                val ids = subscriptions.map { it.itunesId }
                podcastRepository.findByIds(ids)
            }
            .subscribeOn(schedulers.io())
            .subscribeBy(
                onNext = { subscriptions.postValue(it) },
                onComplete = {},
                onError = { Logger.e(it.toString()) }
            )
            .registerDisposable()
    }
}