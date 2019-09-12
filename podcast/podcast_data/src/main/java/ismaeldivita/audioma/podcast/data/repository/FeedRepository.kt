package ismaeldivita.audioma.podcast.data.repository

import android.text.format.DateUtils
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ismaeldivita.audioma.core.data.preferences.Preferences
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.interactor.invoke
import ismaeldivita.audioma.core.util.time.TimeProvider
import ismaeldivita.audioma.podcast.data.interactor.feed.GetFeed
import ismaeldivita.audioma.podcast.data.model.FeedSection
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.repository.helper.GetGenreSectionsCache
import ismaeldivita.audioma.podcast.data.storage.database.dao.FeedDAO

internal class FeedRepository(
    private val getFeed: GetFeed,
    private val feedDAO: FeedDAO,
    private val genreRepository: Repository<Genre>,
    private val podcastRepository: Repository<Podcast>,
    private val getGenreSectionsCache: GetGenreSectionsCache,
    private val preferences: Preferences,
    private val timeProvider: TimeProvider
) : Repository<FeedSection> {

    companion object {
        val LAST_UPDATE_KEY = Preferences.Key.Long("feed_last_update")
        const val CACHE_TTL = DateUtils.DAY_IN_MILLIS
    }

    override fun add(element: FeedSection): Completable = throw UnsupportedOperationException()

    override fun findById(id: Any): Maybe<FeedSection> = throw UnsupportedOperationException()

    override fun findByIds(vararg ids: Any): Single<List<FeedSection>> =
        throw UnsupportedOperationException()

    override fun remove(element: FeedSection): Completable = throw UnsupportedOperationException()

    override fun addAll(elements: List<FeedSection>): Completable {
        TODO()
    }

    override fun getAll(): Single<List<FeedSection>> =
        if (cacheExpired()) {
            getFeed()
                .flatMap { updateDatabase(it) }
                .onErrorResumeNext(getCache())
        } else {
            getCache()
        }

    override fun clear(): Completable = Completable.fromCallable { feedDAO.deleteAllGenreSection() }

    private fun getCache(): Single<List<FeedSection>> {
        val dummyFeedProvider = Single.just(listOf<Pair<Int, FeedSection>>(-1 to FeedSection.Foo))

        return Single.merge(
            getGenreSectionsCache(),
            dummyFeedProvider
        ).toList()
            .map { sectionsList ->
                sectionsList.flatten()
                    .sortedBy { (order, _) -> order }
                    .map { it.second }
            }
    }

    private fun updateDatabase(feed: List<FeedSection>): Single<List<FeedSection>> {
        TODO()
    }

    private fun cacheExpired(): Boolean {
        val lastUpdate = preferences.read(LAST_UPDATE_KEY) ?: 0
        val cacheDuration = timeProvider.getCurrentTimeMillis() - lastUpdate

        return cacheDuration >= CACHE_TTL
    }
}