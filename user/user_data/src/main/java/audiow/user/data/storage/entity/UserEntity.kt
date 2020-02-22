package audiow.user.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
internal data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String?,
    val photoUrl: String?,
    val signInMethod: String
)