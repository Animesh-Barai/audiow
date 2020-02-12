package audiow.podcast.data.storage.database.entity.discover

import androidx.room.*
import audiow.podcast.data.storage.database.entity.GenreEntity
import audiow.podcast.data.storage.database.entity.PodcastEntity

internal data class FeedGenreSectionWrapperEntity(
    @Embedded val section: DiscoverGenreSectionEntity,

    @Relation(parentColumn = "genreId", entityColumn = "genreSectionId")
    val podcasts: List<DiscoverGenreSectionPodcastsEntity>
)

@Entity(
    tableName = "DISCOVER_GENRE_SECTION",
    foreignKeys = [
        ForeignKey(
            entity = GenreEntity::class,
            parentColumns = ["id"],
            childColumns = ["genreId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
internal data class DiscoverGenreSectionEntity(
    @PrimaryKey val genreId: Long,
    val order: Int
)

@Entity(
    tableName = "DISCOVER_GENRE_SECTION_PODCASTS",
    primaryKeys = ["podcastId", "genreSectionId"],
    foreignKeys = [
        ForeignKey(
            entity = PodcastEntity::class,
            parentColumns = ["id"],
            childColumns = ["podcastId"],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = DiscoverGenreSectionEntity::class,
            parentColumns = ["genreId"],
            childColumns = ["genreSectionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
internal data class DiscoverGenreSectionPodcastsEntity(
    val podcastId: Long,
    val genreSectionId: Long
)