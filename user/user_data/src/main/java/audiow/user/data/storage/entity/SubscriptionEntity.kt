package audiow.user.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Subscription")
internal data class SubscriptionEntity(
    @PrimaryKey
    val id: String,
    val feedUrl: String,
    val itunesId: Long
)