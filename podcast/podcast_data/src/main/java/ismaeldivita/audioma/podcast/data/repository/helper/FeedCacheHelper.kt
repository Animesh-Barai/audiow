package ismaeldivita.audioma.podcast.data.repository.helper

import io.reactivex.Completable
import io.reactivex.Single
import ismaeldivita.audioma.podcast.data.model.FeedSection

internal interface FeedCacheHelper {

    fun getAll(): Single<List<Pair<Int, FeedSection>>>

    fun addAll(elements: List<FeedSection>): Completable

    fun delete(): Completable

}