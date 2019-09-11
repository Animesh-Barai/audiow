package ismaeldivita.audioma.podcast.data.storage.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "FEED_GENRE_SECTION",
    foreignKeys = [ForeignKey(
        entity = GenreEntity::class,
        parentColumns = ["id"],
        childColumns = ["genreId"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = PodcastEntity::class,
        parentColumns = ["id"],
        childColumns = ["podcastId"],
        onDelete = ForeignKey.CASCADE
    )],
    primaryKeys = ["genreId", "podcastId"]
)
internal data class FeedGenreSectionEntity(
    val genreId: Int,
    val podcastId: Int
)