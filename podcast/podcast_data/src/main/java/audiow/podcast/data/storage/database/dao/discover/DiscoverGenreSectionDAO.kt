package audiow.podcast.data.storage.database.dao.discover

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import audiow.podcast.data.storage.database.entity.discover.DiscoverGenreSectionEntity
import audiow.podcast.data.storage.database.entity.discover.DiscoverGenreSectionPodcastsEntity
import audiow.podcast.data.storage.database.entity.discover.FeedGenreSectionWrapperEntity

@Dao
internal abstract class DiscoverGenreSectionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insert(entity: DiscoverGenreSectionEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insertAllPodcasts(entities: List<DiscoverGenreSectionPodcastsEntity>)

    @Query("SELECT * FROM discover_genre_section")
    abstract fun getAllGenreSections(): Single<List<FeedGenreSectionWrapperEntity>>

    @Query("DELETE FROM discover_genre_section")
    abstract fun deleteAll(): Completable

    @Transaction
    open fun insertGenreSections(
        sections: Map<DiscoverGenreSectionEntity, List<DiscoverGenreSectionPodcastsEntity>>
    ) {
        sections.forEach {
            insert(it.key)
            insertAllPodcasts(it.value)
        }
    }
}