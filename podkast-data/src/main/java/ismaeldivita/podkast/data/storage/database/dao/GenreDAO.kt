package ismaeldivita.podkast.data.storage.database.dao

import androidx.room.*
import io.reactivex.Single
import ismaeldivita.podkast.data.storage.database.entity.*
import ismaeldivita.podkast.service.model.Genre

@Dao
internal abstract class GenreDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(genre: GenreEntity): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract fun update(genre: GenreEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertSubGenre(subGenreEntity: SubGenreEntity)

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
    open fun genreTransaction(genreList: List<Genre>) {
        genreList
            .map { GenreEntity(it.id, it.name, it.detail!!.topPodcastsUrl) }
            .forEach(::upsert)

        genreList
            .map { genre ->
                genre.detail!!.subgenres.map { SubGenreEntity(genre.id, it.id) }
            }
            .flatten()
            .forEach(::insertSubGenre)
    }

}