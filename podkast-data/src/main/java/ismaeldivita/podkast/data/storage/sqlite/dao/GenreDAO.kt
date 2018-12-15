package ismaeldivita.podkast.data.storage.sqlite.dao

import androidx.room.*
import io.reactivex.Single
import ismaeldivita.podkast.data.storage.sqlite.entity.*
import ismaeldivita.podkast.service.model.Genre

@Dao
internal abstract class GenreDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(genre: GenreEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(genreList: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertSubGenre(subGenreEntity: SubGenreEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertSubGenre(subGenreEntityList: List<SubGenreEntity>)

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
    open fun genreTransaction(genreList: List<Genre>) {
        insert(genreList.map { GenreEntity(it.id, it.name, it.detail!!.topPodcastsUrl) })
        insertSubGenre(genreList.map { genre ->
            genre.detail!!.subgenres.map {
                SubGenreEntity(genre.id, it.id)
            }
        }.flatten())
    }

}