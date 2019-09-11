package ismaeldivita.audioma.podcast.data.storage.database.dao

import androidx.room.*
import io.reactivex.Single
import ismaeldivita.audioma.podcast.data.storage.database.entity.FeedGenreSectionEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.FeedGenreSectionPodcastsEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.FeedGenreSectionWrapperEntity

@Dao
internal abstract class FeedDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insert(entity: FeedGenreSectionEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insertAll(entities: List<FeedGenreSectionPodcastsEntity>)

    @Query("SELECT * FROM FEED_GENRE_SECTION")
    abstract fun getAllGenreSections(): Single<List<FeedGenreSectionWrapperEntity>>

    @Query("DELETE FROM FEED_GENRE_SECTION")
    protected abstract fun deleteAllGenreSection()

    @Transaction
    open fun insertGenreSection(
        sections: HashMap<FeedGenreSectionEntity, List<FeedGenreSectionPodcastsEntity>>
    ) {
        deleteAllGenreSection()

        sections.forEach {
            insert(it.key)
            insertAll(it.value)
        }
    }
}