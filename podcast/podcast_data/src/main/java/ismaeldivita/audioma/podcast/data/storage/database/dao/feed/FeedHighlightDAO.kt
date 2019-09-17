package ismaeldivita.audioma.podcast.data.storage.database.dao.feed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import ismaeldivita.audioma.podcast.data.storage.database.entity.feed.FeedHighlightEntity

@Dao
internal abstract class FeedHighlightDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entity: FeedHighlightEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(entities: List<FeedHighlightEntity>): Completable

    @Query("SELECT * FROM FEED_HIGHLIGHT")
    abstract fun getAllHighlights(): Single<List<FeedHighlightEntity>>

    @Query("DELETE FROM FEED_HIGHLIGHT")
    abstract fun deleteAll(): Completable

}