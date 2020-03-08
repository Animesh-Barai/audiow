package audiow.podcast.feature.detail.ui.detail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.subscribeBy
import audiow.core.android.viewmodel.BaseViewModel
import audiow.core.data.repository.Repository
import audiow.core.data.repository.RepositoryWatcher
import audiow.core.util.reactive.scheduler.SchedulersProvider
import audiow.podcast.data.model.Feed
import audiow.podcast.data.model.Podcast
import audiow.podcast.feature.detail.ui.detail.recyclerview.FeedItem
import audiow.user.data.model.Subscription
import toExternalId
import javax.inject.Inject

internal class PodcastDetailViewModel @Inject constructor(
    private val podcastRepository: Repository<Podcast>,
    private val subscriptionRepository: Repository<Subscription>,
    private val subscriptionWatcher: RepositoryWatcher<Subscription>,
    private val feedRepository: Repository<Feed>,
    private val feedWatcher: RepositoryWatcher<Feed>,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    private val header = MutableLiveData<FeedItem.Header>()
    private val episodes = MutableLiveData<List<FeedItem.FeedEpisode>>()

    val feedItems = MediatorLiveData<List<FeedItem>>().apply {
        fun updateMediator() {
            value = listOfNotNull(
                header.value,
                *episodes.value?.toTypedArray() ?: emptyArray()
            )
        }
        addSource(header) { updateMediator() }
        addSource(episodes) { updateMediator() }
    }

    fun init(podcastId: Long) {
        if (feedItems.value.isNullOrEmpty()) {
            loadPodcastHeader(podcastId)
        }
    }

    fun onSubscriptionChanges(isSubscribed: Boolean) {
        val podcast = header.value!!.podcast
        val subscription = Subscription(
            id = podcast.toExternalId(),
            feedUrl = podcast.rssUrl,
            itunesId = podcast.id
        )
        if (isSubscribed) {
            subscriptionRepository.add(subscription)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.main())
                .subscribe()
                .registerDisposable()
        } else {
            subscriptionRepository.remove(subscription)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.main())
                .subscribe()
                .registerDisposable()
        }
    }

    private fun loadPodcastHeader(podcastId: Long) {
        podcastRepository.findById(podcastId)
            .flatMap { podcast ->
                subscriptionRepository.findById(podcast.toExternalId())
                    .map { true }
                    .defaultIfEmpty(false)
                    .map { podcast to it }
            }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.main())
            .subscribeBy(
                onComplete = {
                    /**
                     * TODO
                     *  There is no cache for this podcast and the network request probably
                     *  failed. The UI for this scenario should be a RETRY.
                     */
                },
                onSuccess = { (podcast, isSubscribed) ->
                    header.value = FeedItem.Header(podcast, isSubscribed)
                    watchSubscriptionUpdates(podcast)
                    loadFeed(podcastId)
                }
            )
            .registerDisposable()
    }

    private fun watchSubscriptionUpdates(podcast: Podcast) {
        subscriptionWatcher.onChanged()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.main())
            .subscribeBy(onNext = { subscriptions ->
                header.value = FeedItem.Header(podcast, subscriptions.any {
                    it.id == podcast.toExternalId()
                })
            })
            .registerDisposable()
    }

    private fun loadFeed(podcastId: Long) {
        feedRepository.findById(podcastId)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.main())
            .subscribeBy(
                onComplete = {
                    // TODO No cache for this feed. Implement skeleton UI loading experience
                    watchForFeedUpdates(podcastId)
                },
                onSuccess = {
                    updateFeedLiveData(it)
                    watchForFeedUpdates(podcastId)
                }
            )
            .registerDisposable()
    }

    private fun updateFeedLiveData(feed: Feed) {
        // TODO Implement extra FeedItem updates (description, donation button, etc...)
        episodes.value = feed.episodes.map { FeedItem.FeedEpisode(it) }
    }

    private fun watchForFeedUpdates(podcastId: Long) {
        feedWatcher.onItemChanged(podcastId)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.main())
            .subscribeBy(
                onNext = {
                    updateFeedLiveData(it)
                },
                onError = {
                    /**
                     * TODO
                     *  There is nothing to do here for now. The chain for updates is terminated
                     *  which is probably indicating there is no internet. A good solution on the
                     *  future is to listen connectivity status updates and re-subscribe.
                     *  If the UI does not contain any feed cache a retry should be displayed.
                     */
                }
            )
            .registerDisposable()
    }
}