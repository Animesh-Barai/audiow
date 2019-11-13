package ismaeldivita.audioma.podcast.data.repository.discover

import android.text.format.DateUtils
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ismaeldivita.audioma.core.data.preferences.Preferences
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.interactor.invoke
import ismaeldivita.audioma.core.util.time.TimeProvider
import ismaeldivita.audioma.podcast.data.interactor.feed.GetFeed
import ismaeldivita.audioma.podcast.data.model.DiscoverItem
import javax.inject.Inject

internal class DiscoverRepository @Inject constructor(
    private val getFeed: GetFeed,
    private val cacheHelpers: Set<@JvmSuppressWildcards DiscoverCacheHelper>,
    private val preferences: Preferences,
    private val timeProvider: TimeProvider
) : Repository<DiscoverItem> {

    companion object {
        val LAST_UPDATE_KEY = Preferences.Key.Long("feed_last_update")
        const val CACHE_TTL = DateUtils.DAY_IN_MILLIS
    }

    override fun onChanged(id: Any): Observable<List<DiscoverItem>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemChanged(id: Any): Observable<DiscoverItem> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(element: DiscoverItem) = throw UnsupportedOperationException()

    override fun findById(id: Any) = throw UnsupportedOperationException()

    override fun findByIds(ids: List<Any>) = throw UnsupportedOperationException()

    override fun remove(element: DiscoverItem) = throw UnsupportedOperationException()

    override fun addAll(elements: List<DiscoverItem>) =
        Completable.merge(cacheHelpers.map { it.addAll(elements) })

    override fun getAll(): Single<List<DiscoverItem>> =
        if (cacheExpired()) {
            getFeed()
                .flatMap {
                    clear()
                        .andThen( addAll(it))
                        .toSingleDefault(it)
                }
                .doOnSuccess {
                    preferences.write(LAST_UPDATE_KEY, timeProvider.getCurrentTimeMillis())
                }
                .onErrorResumeNext { error ->
                    getCache().flatMap {
                        if (it.isEmpty()) Single.error(error) else Single.just((it))
                    }
                }
        } else {
            getCache()
        }

    override fun clear(): Completable = Completable.merge(cacheHelpers.map { it.delete() })

    private fun getCache(): Single<List<DiscoverItem>> =
        Single.merge(cacheHelpers.map { it.getAll() })
            .toList()
            .map { cache ->
                cache.flatten()
                    .sortedBy { it.first }
                    .map { it.second }
            }

    private fun cacheExpired(): Boolean {
        val lastUpdate = preferences.read(LAST_UPDATE_KEY) ?: 0
        val cacheDuration = timeProvider.getCurrentTimeMillis() - lastUpdate

        return cacheDuration >= CACHE_TTL
    }
}