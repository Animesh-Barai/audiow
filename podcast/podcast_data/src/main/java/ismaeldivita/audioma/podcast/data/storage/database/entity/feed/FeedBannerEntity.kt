package ismaeldivita.audioma.podcast.data.storage.database.entity.feed

import androidx.room.*
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastEntity

internal data class FeedBannerWrapperEntity(
    @Embedded val banner: FeedBannerEntity,

    @Relation(parentColumn = "id", entityColumn = "bannerId")
    val podcasts: List<FeedBannerPodcastsEntity>
)

@Entity(tableName = "FEED_BANNER")
internal data class FeedBannerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val order: Int
)

@Entity(
    tableName = "FEED_BANNER_PODCASTS",
    primaryKeys = ["bannerId", "podcastId"],
    foreignKeys = [
        ForeignKey(
            entity = FeedBannerEntity::class,
            parentColumns = ["id"],
            childColumns = ["bannerId"],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = PodcastEntity::class,
            parentColumns = ["id"],
            childColumns = ["podcastId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
internal data class FeedBannerPodcastsEntity(
    val bannerId: Long,
    val podcastId: Long
)