package audiow.podcast.data.storage.database.entity.discover

import androidx.room.*
import audiow.podcast.data.storage.database.entity.PodcastEntity

internal data class DiscoverBannerWrapperEntity(
    @Embedded val banner: DiscoverBannerEntity,

    @Relation(parentColumn = "id", entityColumn = "bannerId")
    val podcasts: List<DiscoverBannerPodcastEntity>
)

@Entity(tableName = "discover_banner")
internal data class DiscoverBannerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val order: Int
)

@Entity(
    tableName = "discover_banner_podcasts",
    primaryKeys = ["bannerId", "podcastId"],
    foreignKeys = [
        ForeignKey(
            entity = DiscoverBannerEntity::class,
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
internal data class DiscoverBannerPodcastEntity(
    val bannerId: Long,
    val podcastId: Long
)