package ismaeldivita.podkast.data.storage.sqlite.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import ismaeldivita.podkast.data.storage.sqlite.entity.GenreDB

@Dao
abstract class GenreDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(genre: GenreDB)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(genreList: List<GenreDB>)

    @Query("SELECT * FROM GENRE")
    abstract fun getAll(): Single<List<GenreDB>>

}