package ismaeldivita.audioma.podcast.data.storage.database.dao.feed

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import ismaeldivita.audioma.podcast.data.storage.database.entity.feed.FeedGenreSectionEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.feed.FeedGenreSectionPodcastsEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.feed.FeedGenreSectionWrapperEntity

@Dao
internal abstract class FeedGenreSectionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insert(entity: FeedGenreSectionEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insertAllPodcasts(entities: List<FeedGenreSectionPodcastsEntity>)

    @Query("SELECT * FROM FEED_GENRE_SECTION")
    abstract fun getAllGenreSections(): Single<List<FeedGenreSectionWrapperEntity>>

    @Query("DELETE FROM FEED_GENRE_SECTION")
    abstract fun deleteAll(): Completable

    @Transaction
    open fun insertGenreSections(
        sections: Map<FeedGenreSectionEntity, List<FeedGenreSectionPodcastsEntity>>
    ) {
        sections.forEach {
            insert(it.key)
            insertAllPodcasts(it.value)
        }
    }
}