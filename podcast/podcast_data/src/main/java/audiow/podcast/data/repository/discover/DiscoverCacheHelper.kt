package audiow.podcast.data.repository.discover

import io.reactivex.Completable
import io.reactivex.Single
import audiow.podcast.data.model.Discover

internal interface DiscoverCacheHelper {

    fun getAll(): Single<List<Pair<Int, Discover>>>

    fun addAll(elements: List<Discover>): Completable

    fun delete(): Completable

}