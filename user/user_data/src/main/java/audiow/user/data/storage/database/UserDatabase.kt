package audiow.user.data.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import audiow.user.data.storage.database.dao.SubscriptionDAO
import audiow.user.data.storage.database.dao.UserDAO
import audiow.user.data.storage.database.entity.SubscriptionEntity
import audiow.user.data.storage.database.entity.UserEntity

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