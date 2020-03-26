package audiow.user.data.storage.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
internal data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String?,
    val photoUrl: String?,
    val signInMethod: String,
    val isSelected: Boolean
)