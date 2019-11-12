package ismaeldivita.audioma.podcast.data.repository.helper

import io.reactivex.Completable
import io.reactivex.Single
import ismaeldivita.audioma.podcast.data.model.DiscoverItem

internal interface DiscoverCacheHelper {

    fun getAll(): Single<List<Pair<Int, DiscoverItem>>>

    fun addAll(elements: List<DiscoverItem>): Completable

    fun delete(): Completable

}