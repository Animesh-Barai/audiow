package ismaeldivita.audioma.podcast.data.storage.database.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import ismaeldivita.audioma.podcast.data.storage.database.entity.FeedEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.FeedEpisodeEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.FeedPodcastWrapper

@Dao
internal abstract class FeedDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun insert(feed: FeedEntity) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insert(episodes: List<FeedEpisodeEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun update(feed: FeedEntity)

    @Query("SELECT * FROM FEED WHERE id=:id")
    abstract fun findById(id: Long): Maybe<FeedPodcastWrapper>

    @Query("SELECT * FROM FEED WHERE id IN(:ids)")
    abstract fun findByIds(ids: List<Long>): Single<List<FeedPodcastWrapper>>

    @Query("SELECT * FROM FEED")
    abstract fun getAllFeeds(): Single<List<FeedPodcastWrapper>>

    @Query("SELECT * FROM FEED WHERE id=:id")
    abstract fun onItemChanged(id: Long): Observable<FeedPodcastWrapper>

    @Query("SELECT * FROM FEED")
    abstract fun onChanged(): Observable<List<FeedPodcastWrapper>>

    @Delete
    abstract fun delete(model: FeedEntity): Completable

    @Query("DELETE FROM FEED")
    abstract fun deleteAll(): Completable

    @Transaction
    open fun upsert(entity: FeedEntity) {
        val id = insert(entity)
        if (id == -1L) {
            update(entity)
        }
    }

    @Transaction
    open fun insert(feed: FeedEntity, episodes: List<FeedEpisodeEntity>) {
        upsert(feed)
        insert(episodes)
    }

    @Transaction
    open fun insert(feeds: List<FeedEntity>, episodes: List<FeedEpisodeEntity>) {
        feeds.forEach(::upsert)
        insert(episodes)
    }
}