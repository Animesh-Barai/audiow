package audiow.user.data.storage.dao

import androidx.room.*
import audiow.user.data.storage.entity.SubscriptionEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
internal abstract class SubscriptionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insert(entity: SubscriptionEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun update(entity: SubscriptionEntity): Completable

    @Query("SELECT * FROM Subscription")
    abstract fun getAll(): Single<List<SubscriptionEntity>>

    @Query("SELECT * FROM Subscription WHERE id=:id")
    abstract fun findById(id: Long): Maybe<SubscriptionEntity>

    @Query("SELECT * FROM Subscription WHERE id IN (:ids)")
    abstract fun findByIds(ids: List<Long>): Single<List<SubscriptionEntity>>

    @Query("DELETE FROM Subscription")
    abstract fun deleteAll(): Completable

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
}