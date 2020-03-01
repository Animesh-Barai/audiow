package audiow.user.data.storage.database.dao

import androidx.room.*
import audiow.user.data.storage.database.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
internal abstract class UserDAO {

    @Query("SELECT * FROM User")
    abstract fun getAll(): Single<List<UserEntity>>

    @Query("SELECT * FROM User WHERE id=:id")
    abstract fun findById(id: String): Maybe<UserEntity>

    @Query("SELECT * FROM User WHERE id IN (:ids)")
    abstract fun findByIds(ids: List<String>): Single<List<UserEntity>>

    @Query("SELECT * FROM User WHERE isSelected=1")
    abstract fun getSelectedUser(): Single<UserEntity>

    @Query("DELETE FROM User")
    abstract fun deleteAll(): Completable

    @Delete
    abstract fun delete(entity: UserEntity): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insert(userEntity: UserEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun update(userEntity: UserEntity): Completable

    @Query("UPDATE User SET isSelected=0 WHERE isSelected=1")
    protected abstract fun unselectAllUsers()

    @Transaction
    open fun upsert(entity: UserEntity) {
        /** Unselect the current user before add the new selected user */
        if (entity.isSelected) {
            unselectAllUsers()
        }

        val id = insert(entity)
        if (id == -1L) {
            update(entity)
        }
    }

    @Transaction
    open fun upsert(entityList: List<UserEntity>) {
        entityList.forEach(::upsert)
    }
}