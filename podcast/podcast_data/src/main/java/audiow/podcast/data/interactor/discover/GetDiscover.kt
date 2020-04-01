package audiow.podcast.data.interactor.discover

import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import audiow.core.interactor.Interactor
import audiow.core.util.reactive.scheduler.SchedulersProvider
import audiow.podcast.data.interactor.podcast.GetPreferredGenrePodcasts
import audiow.podcast.data.interactor.podcast.GetTopPodcasts
import audiow.podcast.data.interactor.podcast.GetPreferredGenrePodcasts.*
import audiow.podcast.data.model.Discover
import javax.inject.Inject

interface GetDiscover : Interactor<Unit, Single<List<Discover>>>

internal class GetDiscoverImpl @Inject constructor(
    private val genreSectionsFeed: GetPreferredGenrePodcasts,
    private val getTopPodcasts: GetTopPodcasts,
    private val scheduler: SchedulersProvider
) : GetDiscover {

    override fun invoke(param: Unit): Single<List<Discover>> {
        return Singles.zip(
            getTopPodcasts(8).subscribeOn(scheduler.io()),
            genreSectionsFeed(Input(count = 10, limit = 15)).subscribeOn(scheduler.io())
        ) { topPodcasts, topPreferredGenresPodcasts ->

            val banner = Discover.Banner(topPodcasts.take(5))

            val highlights = topPodcasts.drop(5).map { Discover.Highlight(it) }

            /** Filter the top podcasts from the genre sections to avoid duplicated discover items */
            val filteredGenrePodcasts = topPreferredGenresPodcasts.map { (genre, podcasts) ->
                Discover.GenreSection(genre, podcasts.filterNot { topPodcasts.contains(it) })
            }

            /**
             * For now we are manually building the discover list with the banner and a highlight
             * after every 2 genre sections.
             *
             * TODO improve this to work dynamically with a FeedConfigurationInput.
             *  The configuration should be able to be replaced by RemoteConfig
             **/
            listOf(
                banner,
                *filteredGenrePodcasts.take(2).toTypedArray(),
                highlights.component1(),
                *filteredGenrePodcasts.drop(2).take(2).toTypedArray(),
                highlights.component2(),
                *filteredGenrePodcasts.drop(4).take(2).toTypedArray(),
                highlights.component3(),
                *filteredGenrePodcasts.drop(6).toTypedArray()
            )
        }
    }

}