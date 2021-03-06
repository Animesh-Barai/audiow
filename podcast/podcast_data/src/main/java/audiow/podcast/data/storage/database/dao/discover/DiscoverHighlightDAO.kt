package audiow.podcast.data.storage.database.dao.discover

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import audiow.podcast.data.storage.database.entity.discover.DiscoverHighlightEntity

@Dao
internal abstract class DiscoverHighlightDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entity: DiscoverHighlightEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(entities: List<DiscoverHighlightEntity>): Completable

    @Query("SELECT * FROM discover_highlight")
    abstract fun getAllHighlights(): Single<List<DiscoverHighlightEntity>>

    @Query("DELETE FROM discover_highlight")
    abstract fun deleteAll(): Completable

}