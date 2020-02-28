package audiow.user.data.storage.database.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import audiow.user.data.storage.database.dao.entity.SubscriptionEntity
import audiow.user.data.storage.database.dao.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        SubscriptionEntity::class
    ],
    version = 1,
    exportSchema = false
)
internal abstract class UserDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_FILE_NAME = "USER"
    }

    abstract fun userDAO(): UserDAO

    abstract fun subscriptionDAO(): SubscriptionDAO

}