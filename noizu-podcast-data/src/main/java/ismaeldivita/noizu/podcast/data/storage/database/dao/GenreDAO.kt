package ismaeldivita.noizu.podcast.data.storage.database.dao

import androidx.annotation.VisibleForTesting
import androidx.room.*
import io.reactivex.Single
import ismaeldivita.noizu.podcast.data.storage.database.entity.*

@Dao
internal abstract class GenreDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @VisibleForTesting
    protected abstract fun insert(genre: GenreEntity): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun update(genre: GenreEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @VisibleForTesting
    protected abstract fun insertSubGenre(subGenreEntity: SubGenreEntity)

    @Query("SELECT * FROM GENRE WHERE id=:id")
    abstract fun findById(id: Int): Single<GenreEntity>

    @Query("SELECT * FROM GENRE WHERE id IN (:ids)")
    abstract fun findByIds(ids: List<Int>): Single<GenreEntity>

    @Query("SELECT * FROM GENRE")
    abstract fun getAll(): Single<List<GenreEntity>>

    @Query("SELECT * FROM GENRE")
    abstract fun getAllWithSubGenres(): Single<List<GenreWithSubGenre>>

    @Query("DELETE FROM GENRE")
    abstract fun deleteAll()

    @Transaction
    open fun upsert(entity: GenreEntity) {
        val id = insert(entity)
        if (id == -1L) {
            update(entity)
        }
    }

    @Transaction
    open fun genreTransaction(
        genreEntityList: List<GenreEntity>,
        subGenreEntityList: List<SubGenreEntity>
    ) {
        genreEntityList.forEach(::upsert)
        subGenreEntityList.forEach(::insertSubGenre)
    }

}