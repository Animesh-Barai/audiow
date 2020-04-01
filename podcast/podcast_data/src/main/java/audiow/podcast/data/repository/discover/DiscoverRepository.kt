package audiow.podcast.data.repository.discover

import android.text.format.DateUtils
import io.reactivex.Completable
import io.reactivex.Single
import audiow.core.data.preferences.Preferences
import audiow.core.data.repository.Repository
import audiow.core.interactor.invoke
import audiow.core.util.reactive.scheduler.SchedulersProvider
import audiow.core.util.time.TimeProvider
import audiow.podcast.data.interactor.discover.GetDiscover
import audiow.podcast.data.model.Discover
import javax.inject.Inject

internal class DiscoverRepository @Inject constructor(
    private val getDiscover: GetDiscover,
    private val cacheHelpers: Set<@JvmSuppressWildcards DiscoverCacheHelper>,
    private val preferences: Preferences,
    private val timeProvider: TimeProvider,
    private val schedulers: SchedulersProvider
) : Repository<Discover> {

    companion object {
        private val LAST_UPDATE_KEY = Preferences.Key.Long("feed_last_update")
        private const val CACHE_TTL = DateUtils.DAY_IN_MILLIS
    }

    override fun add(element: Discover) = throw UnsupportedOperationException()

    override fun findById(id: Any) = throw UnsupportedOperationException()

    override fun findByIds(ids: List<Any>) = throw UnsupportedOperationException()

    override fun remove(element: Discover) = throw UnsupportedOperationException()

    override fun addAll(elements: List<Discover>) =
        Completable.merge(cacheHelpers.map { it.addAll(elements).subscribeOn(schedulers.io()) })

    override fun getAll(): Single<List<Discover>> =
        getCache()
            .takeUnless { cacheExpired() }
            ?: getDiscover()
                .flatMap {
                    clear().andThen(addAll(it)).toSingleDefault(it)
                }
                .doOnSuccess {
                    preferences.write(LAST_UPDATE_KEY, timeProvider.getCurrentTimeMillis())
                }
                .onErrorResumeNext { error ->
                    getCache()
                        .flatMap { if (it.isEmpty()) Single.error(error) else Single.just((it)) }
                }


    override fun clear(): Completable = Completable.merge(
        cacheHelpers.map { it.delete().subscribeOn(schedulers.io()) }
    )

    private fun getCache(): Single<List<Discover>> =
        Single.merge(cacheHelpers.map { it.getAll().subscribeOn(schedulers.io()) })
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