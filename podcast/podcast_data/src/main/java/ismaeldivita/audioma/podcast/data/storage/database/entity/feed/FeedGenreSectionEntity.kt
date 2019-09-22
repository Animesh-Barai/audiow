package ismaeldivita.audioma.podcast.data.storage.database.entity.feed

import androidx.room.*
import ismaeldivita.audioma.podcast.data.storage.database.entity.GenreEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastEntity

internal data class FeedGenreSectionWrapperEntity(
    @Embedded val section: FeedGenreSectionEntity,

    @Relation(parentColumn = "genreId", entityColumn = "genreSectionId")
    val podcasts: List<FeedGenreSectionPodcastsEntity>
)

@Entity(
    tableName = "FEED_GENRE_SECTION",
    foreignKeys = [
        ForeignKey(
            entity = GenreEntity::class,
            parentColumns = ["id"],
            childColumns = ["genreId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
internal data class FeedGenreSectionEntity(
    @PrimaryKey val genreId: Long,
    val order: Int
)

@Entity(
    tableName = "FEED_GENRE_SECTION_PODCASTS",
    primaryKeys = ["podcastId", "genreSectionId"],
    foreignKeys = [
        ForeignKey(
            entity = PodcastEntity::class,
            parentColumns = ["id"],
            childColumns = ["podcastId"],
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = FeedGenreSectionEntity::class,
            parentColumns = ["genreId"],
            childColumns = ["genreSectionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
internal data class FeedGenreSectionPodcastsEntity(
    val podcastId: Long,
    val genreSectionId: Long
)