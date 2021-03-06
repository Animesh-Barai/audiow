package audiow.podcast.data.storage.database.entity

import androidx.room.*

internal data class FeedPodcastWrapper(
    @Embedded val feed: FeedEntity,

    @Relation(parentColumn = "id", entityColumn = "feedId")
    val episodes: List<FeedEpisodeEntity>
)

@Entity(
    tableName = "feed",
    foreignKeys = [
        ForeignKey(
            entity = PodcastEntity::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
            deferred = true
        )
    ]
)
internal data class FeedEntity(
    @PrimaryKey
    val id: Long,
    val description: String,
    val language: String,

    @Embedded(prefix = "metadata_")
    val metadata: Metadata? = null
) {
    data class Metadata(
        val lastModified: String? = null,
        val eTag: String? = null
    )
}

@Entity(
    tableName = "feed_episode",
    foreignKeys = [
        ForeignKey(
            entity = FeedEntity::class,
            parentColumns = ["id"],
            childColumns = ["feedId"],
            onDelete = ForeignKey.CASCADE,
            deferred = true
        )
    ]
)
internal data class FeedEpisodeEntity(
    @PrimaryKey
    val audioFileUrl: String,
    val feedId: Long,
    val title: String,
    val subtitle: String?,
    val description: String,
    val duration: String?,
    val isExplicit: Boolean,
    val publicationDateRFC822: String,
    val coverImageUrl: String?
)