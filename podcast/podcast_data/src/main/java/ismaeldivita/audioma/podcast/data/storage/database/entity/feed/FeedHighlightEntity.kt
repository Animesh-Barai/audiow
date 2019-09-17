package ismaeldivita.audioma.podcast.data.storage.database.entity.feed

import androidx.room.*
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastEntity

@Entity(
    tableName = "FEED_HIGHLIGHT",
    foreignKeys = [
        ForeignKey(
            entity = PodcastEntity::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )]
)
internal data class FeedHighlightEntity(
    @PrimaryKey
    val id: Long,
    val order: Int
)