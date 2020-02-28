package audiow.user.data.storage.database.dao

import androidx.room.*
import audiow.user.data.storage.database.dao.entity.SubscriptionEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

@Dao
internal abstract class SubscriptionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insert(entity: SubscriptionEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insertAll(entities: List<SubscriptionEntity>)

    @Query("DELETE FROM Subscription")
    protected abstract fun deleteAllSync()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun update(entity: SubscriptionEntity): Completable

    @Query("SELECT * FROM Subscription")
    abstract fun getAll(): Single<List<SubscriptionEntity>>

    @Query("SELECT * FROM Subscription WHERE id=:id")
    abstract fun findById(id: String): Maybe<SubscriptionEntity>

    @Query("SELECT * FROM Subscription WHERE id IN (:ids)")
    abstract fun findByIds(ids: List<String>): Single<List<SubscriptionEntity>>

    @Query("SELECT * FROM Subscription")
    abstract fun onChanged(): Observable<List<SubscriptionEntity>>

    @Query("DELETE FROM Subscription")
    abstract fun deleteAll(): Completable

    @Query("DELETE FROM Subscription WHERE id NOT IN (:ids)")
    abstract fun deleteMissingIds(ids: List<String>)

    @Delete
    abstract fun delete(entity: SubscriptionEntity): Completable

    @Transaction
    open fun upsert(entity: SubscriptionEntity) {
        val id = insert(entity)
        if (id == -1L) {
            update(entity)
        }
    }

    @Transaction
    open fun upsert(entityList: List<SubscriptionEntity>) {
        entityList.forEach { entity ->
            val id = insert(entity)
            if (id == -1L) {
                update(entity)
            }
        }
    }

    @Transaction
    open fun updateSubscriptions(entityList: List<SubscriptionEntity>) {
        insertAll(entityList)
        deleteMissingIds(entityList.map { it.id })
    }
}