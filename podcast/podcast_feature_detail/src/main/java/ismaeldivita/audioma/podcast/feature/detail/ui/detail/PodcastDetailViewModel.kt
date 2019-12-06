package ismaeldivita.audioma.podcast.feature.detail.ui.detail

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.subscribeBy
import ismaeldivita.audioma.core.android.viewmodel.BaseViewModel
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.data.repository.RepositoryWatcher
import ismaeldivita.audioma.core.util.reactive.SchedulersProvider
import ismaeldivita.audioma.podcast.data.model.Feed
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.feature.detail.ui.detail.recyclerview.FeedItem
import javax.inject.Inject

internal class PodcastDetailViewModel @Inject constructor(
    private val podcastRepository: Repository<Podcast>,
    private val feedRepository: Repository<Feed>,
    private val feedWatcher: RepositoryWatcher<Feed>,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    val feedItems = MutableLiveData<List<FeedItem>>()

    fun init(podcastId: Long) {
        podcastRepository.findById(podcastId)
            .subscribeOn(schedulersProvider.io())
            .flatMap { podcast ->
                feedRepository.findById(podcastId)
                    .doOnSuccess { feed ->
                        val feedEpisodes = feed.episodes.map(FeedItem::FeedEpisode).toTypedArray()
                        feedItems.postValue(listOf(FeedItem.Header(podcast), *feedEpisodes))
                    }
                    .doOnComplete {
                        feedItems.postValue(listOf(FeedItem.Header(podcast)))
                    }
            }
            .subscribeBy(
                onComplete = { watchFeedUpdates(podcastId) },
                onSuccess = { watchFeedUpdates(podcastId) }
            )
            .registerDisposable()
    }

    private fun watchFeedUpdates(podcastId: Long) {
        feedWatcher.onItemChanged(podcastId)
            .subscribeOn(schedulersProvider.io())
            .subscribeBy {
                val header = feedItems.value!!.first()
                val feedEpisodes = it.episodes.map(FeedItem::FeedEpisode).toTypedArray()
                feedItems.postValue(listOf(header, *feedEpisodes))
            }
            .registerDisposable()
    }
}