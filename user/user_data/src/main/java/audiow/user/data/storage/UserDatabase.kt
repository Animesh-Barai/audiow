package audiow.user.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import audiow.user.data.storage.dao.UserDAO
import audiow.user.data.storage.entity.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 1,
    exportSchema = false
)
internal abstract class UserDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_FILE_NAME = "USER"
    }

    abstract fun userDAO(): UserDAO
}