package ismaeldivita.audioma.podcast.feature.detail.ui.episode

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.subscribeBy
import ismaeldivita.audioma.core.android.viewmodel.BaseViewModel
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.util.reactive.scheduler.SchedulersProvider
import ismaeldivita.audioma.podcast.data.model.Episode
import ismaeldivita.audioma.podcast.data.model.Feed
import ismaeldivita.audioma.podcast.data.model.Podcast
import javax.inject.Inject

internal class EpisodeViewModel @Inject constructor(
    private val feedRepository: Repository<Feed>,
    private val podcastRepository: Repository<Podcast>,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    val episode = MutableLiveData<Episode>()
    val podcast = MutableLiveData<Podcast>()

    fun init(podcastId: Long, episodeId: String) {

        feedRepository.findById(podcastId)
            .map { feed -> feed.episodes.first { it.id == episodeId } }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.main())
            .subscribeBy(onSuccess = {
                episode.value = it
            })
            .registerDisposable()

        podcastRepository.findById(podcastId)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.main())
            .subscribeBy(onSuccess = {
                podcast.value = it
            })
            .registerDisposable()
    }
}