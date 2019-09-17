package ismaeldivita.audioma.podcast.data.storage.database.entity.feed

import androidx.room.*
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastEntity

internal data class FeedHighlightWrapperEntity(
    @Embedded val highlight: FeedHighlightEntity,

    @Relation(parentColumn = "id", entityColumn = "id")
    val podcast: PodcastEntity
)

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
    val id: Int,
    val order: Int
)