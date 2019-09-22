package ismaeldivita.audioma.podcast.data.repository

import android.text.format.DateUtils
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import ismaeldivita.audioma.core.data.preferences.Preferences
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.interactor.invoke
import ismaeldivita.audioma.core.util.reactive.SchedulersProvider
import ismaeldivita.audioma.core.util.time.TimeProvider
import ismaeldivita.audioma.podcast.data.interactor.feed.GetFeed
import ismaeldivita.audioma.podcast.data.model.FeedSection
import ismaeldivita.audioma.podcast.data.repository.helper.FeedCacheHelper
import ismaeldivita.audioma.podcast.data.repository.helper.GenreSectionsCacheHelper
import javax.inject.Inject

internal class FeedRepository @Inject constructor(
    private val getFeed: GetFeed,
    private val cacheHelpers: Set<@JvmSuppressWildcards FeedCacheHelper>,
    private val preferences: Preferences,
    private val timeProvider: TimeProvider
) : Repository<FeedSection> {

    companion object {
        val LAST_UPDATE_KEY = Preferences.Key.Long("feed_last_update")
        const val CACHE_TTL = DateUtils.DAY_IN_MILLIS
    }

    override fun add(element: FeedSection) = throw UnsupportedOperationException()

    override fun findById(id: Any) = throw UnsupportedOperationException()

    override fun findByIds(ids: List<Any>) = throw UnsupportedOperationException()

    override fun remove(element: FeedSection) = throw UnsupportedOperationException()

    override fun addAll(elements: List<FeedSection>) =
        Completable.merge(cacheHelpers.map { it.addAll(elements) })

    override fun getAll(): Single<List<FeedSection>> =
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

    private fun getCache(): Single<List<FeedSection>> =
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