package ismaeldivita.audioma.podcast.data.interactor.feed

import io.reactivex.Single
import ismaeldivita.audioma.core.interactor.Interactor
import ismaeldivita.audioma.core.interactor.invoke
import ismaeldivita.audioma.podcast.data.model.FeedSection
import javax.inject.Inject

interface FetchFeed : Interactor<Unit, Single<List<FeedSection>>>

internal class FetchFeedImpl @Inject constructor(
    private val genreSectionsFeed: GetPreferredFeedGenreSections
) : FetchFeed {

    override fun invoke(param: Unit): Single<List<FeedSection>> {
        return genreSectionsFeed()
            .map { it.map { it as FeedSection } }
    }
}