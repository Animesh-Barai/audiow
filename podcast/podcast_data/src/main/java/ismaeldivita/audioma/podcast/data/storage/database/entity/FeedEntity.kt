package ismaeldivita.audioma.podcast.data.storage.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "FEED_GENRE_SECTION",
    foreignKeys = [ForeignKey(
        entity = GenreEntity::class,
        parentColumns = ["id"],
        childColumns = ["genreId"],
        onDelete = ForeignKey.CASCADE
    )]
)
internal data class FeedGenreSectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val genreId: Int
)

@Entity(
    tableName = "FEED_GENRE_SECTION_PODCASTS",
    foreignKeys = [ForeignKey(
        entity = FeedGenreSectionEntity::class,
        parentColumns = ["id"],
        childColumns = ["genreSectionId"],
        onDelete = ForeignKey.CASCADE
    ),ForeignKey(
        entity = PodcastEntity::class,
        parentColumns = ["id"],
        childColumns = ["podcastId"],
        onDelete = ForeignKey.CASCADE
    )]
)
internal data class FeedGenreSectionPodcastsEntity(
    val genreSectionId: Int,
    val podcastId: Int
)