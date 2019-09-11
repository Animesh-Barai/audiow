package ismaeldivita.audioma.podcast.data.storage.database.dao

import androidx.room.*
import ismaeldivita.audioma.podcast.data.storage.database.entity.FeedGenreSectionEntity

@Dao
internal abstract class FeedDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insert(genreSectionEntity: FeedGenreSectionEntity)

    @Query("DELETE FROM FEED_GENRE_SECTION")
    protected abstract fun deleteAllGenreSection()

    @Transaction
    open fun insertGenreSection(sections: List<FeedGenreSectionEntity>) {
        deleteAllGenreSection()
        sections.forEach(::insert)
    }
}