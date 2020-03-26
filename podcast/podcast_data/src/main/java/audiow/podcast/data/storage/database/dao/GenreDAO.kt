package audiow.podcast.data.storage.database.dao

import androidx.annotation.VisibleForTesting
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import audiow.podcast.data.storage.database.entity.*

@Dao
internal abstract class GenreDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insert(genre: GenreEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun update(genre: GenreEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @VisibleForTesting
    protected abstract fun insertSubGenre(subGenreEntity: SubGenreEntity)

    @Query("SELECT * FROM genre WHERE id=:id")
    abstract fun findById(id: Long): Maybe<GenreEntity>

    @Query("SELECT * FROM genre WHERE id IN (:ids)")
    abstract fun findByIds(ids: List<Long>): Single<List<GenreEntity>>

    @Query("SELECT * FROM genre")
    abstract fun getAll(): Single<List<GenreEntity>>

    @Query("SELECT * FROM genre")
    abstract fun getAllWithSubGenres(): Single<List<GenreWithSubGenre>>

    @Query("DELETE FROM genre")
    abstract fun deleteAll() : Completable

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