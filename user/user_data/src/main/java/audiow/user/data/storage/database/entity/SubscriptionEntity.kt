package audiow.user.data.storage.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "subscription",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
            deferred = true
        )
    ]
)
internal data class SubscriptionEntity(
    @PrimaryKey
    val id: String,
    val feedUrl: String,
    val itunesId: Long,
    val userId: String
)