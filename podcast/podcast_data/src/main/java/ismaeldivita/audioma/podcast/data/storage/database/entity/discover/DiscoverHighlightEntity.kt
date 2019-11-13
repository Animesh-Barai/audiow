package ismaeldivita.audioma.podcast.data.storage.database.entity.discover

import androidx.room.*
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastEntity

@Entity(
    tableName = "DISCOVER_HIGHLIGHT",
    foreignKeys = [
        ForeignKey(
            entity = PodcastEntity::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )]
)
internal data class DiscoverHighlightEntity(
    @PrimaryKey
    val id: Long,
    val order: Int
)