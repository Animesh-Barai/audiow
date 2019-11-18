package ismaeldivita.audioma.podcast.data.storage.database.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ismaeldivita.audioma.podcast.data.storage.database.entity.FeedEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.FeedEpisodeEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.FeedPodcastWrapper
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastEntity

@Dao
internal abstract class FeedDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun insert(feed: FeedEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insert(episodes: List<FeedEpisodeEntity>)

    @Query("SELECT * FROM FEED WHERE id=:id")
    abstract fun findById(id: Long): Maybe<FeedPodcastWrapper>

    @Query("SELECT * FROM FEED WHERE id IN(:ids)")
    abstract fun findByIds(ids: List<Long>): Single<List<FeedPodcastWrapper>>

    @Query("SELECT * FROM FEED")
    abstract fun getAllFeeds(): Single<List<FeedPodcastWrapper>>

    @Delete
    abstract fun delete(model: FeedEntity): Completable

    @Query("DELETE FROM FEED")
    abstract fun deleteAll(): Completable

    @Transaction
    open fun insert(feed: FeedEntity, episodes: List<FeedEpisodeEntity>) {
        insert(feed)
        insert(episodes)
    }

    @Transaction
    open fun insert(feeds: List<FeedEntity>, episodes: List<FeedEpisodeEntity>) {
        feeds.forEach(::insert)
        insert(episodes)
    }
}