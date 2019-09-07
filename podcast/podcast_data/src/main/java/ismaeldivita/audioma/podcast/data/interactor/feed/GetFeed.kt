package ismaeldivita.audioma.podcast.data.interactor.feed

import io.reactivex.Single
import ismaeldivita.audioma.core.interactor.Interactor
import ismaeldivita.audioma.core.interactor.invoke
import ismaeldivita.audioma.podcast.data.interactor.genre.GetPreferredGenres
import ismaeldivita.audioma.podcast.data.model.FeedSection

interface GetFeed : Interactor<Unit, Single<List<FeedSection>>>

internal class GetFeedImpl(
    private val getPreferredGenres: GetPreferredGenres
) : GetFeed {

    override fun invoke(param: Unit): Single<List<FeedSection>> =
        getPreferredGenres()
            .map { emptyList<FeedSection>() }

}