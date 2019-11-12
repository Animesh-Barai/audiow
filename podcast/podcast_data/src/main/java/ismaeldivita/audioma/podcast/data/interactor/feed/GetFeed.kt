package ismaeldivita.audioma.podcast.data.interactor.feed

import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import ismaeldivita.audioma.core.interactor.Interactor
import ismaeldivita.audioma.podcast.data.interactor.podcast.GetPreferredGenrePodcasts
import ismaeldivita.audioma.podcast.data.interactor.podcast.GetTopPodcasts
import ismaeldivita.audioma.podcast.data.interactor.podcast.GetPreferredGenrePodcasts.*
import ismaeldivita.audioma.podcast.data.model.DiscoverItem
import javax.inject.Inject

interface GetFeed : Interactor<Unit, Single<List<DiscoverItem>>>

internal class GetFeedImpl @Inject constructor(
    private val genreSectionsFeed: GetPreferredGenrePodcasts,
    private val getTopPodcasts: GetTopPodcasts
) : GetFeed {

    override fun invoke(param: Unit): Single<List<DiscoverItem>> {
        return Singles.zip(
            getTopPodcasts(8),
            genreSectionsFeed(Input(count = 6, limit = 15))
        ) { topPodcasts, topPreferredGenresPodcasts ->

            val banner = DiscoverItem.Banner(topPodcasts.take(5))

            val highlights = topPodcasts.drop(5).map { DiscoverItem.Highlight(it) }

            /** Filter the top podcasts from the genre sections to avoid duplicated feed items */
            val filteredGenrePodcasts = topPreferredGenresPodcasts.map { (genre, podcasts) ->
                DiscoverItem.GenreSection(genre, podcasts.filterNot { topPodcasts.contains(it) })
            }

            /**
             * For now we are manually building a feed with the banner and a highlight after
             * every 2 genre sections.
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
                highlights.component3()
            )
        }
    }

}