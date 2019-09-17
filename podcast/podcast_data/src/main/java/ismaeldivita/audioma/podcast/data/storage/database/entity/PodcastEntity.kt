package ismaeldivita.audioma.podcast.data.storage.database.entity

import androidx.room.*

internal data class PodcastWrapperEntity(
    @Embedded val podcast: PodcastEntity,

    @Relation(parentColumn = "id", entityColumn = "podcastIdFk")
    val artworkList: List<PodcastArtworkEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "podcastId",
        entity = PodcastAndGenreMapEntity::class,
        projection = ["genreId"]
    )
    val genreIds: List<Long>
)

@Entity(
    tableName = "PODCAST",
    foreignKeys = [ForeignKey(
        entity = GenreEntity::class,
        parentColumns = ["id"],
        childColumns = ["primaryGenre"],
        onDelete = ForeignKey.CASCADE
    )]
)
internal data class PodcastEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val artistName: String,
    val primaryGenre: Long,
    val rssUrl: String,
    val explicit: Boolean
)

@Entity(
    tableName = "PODCAST_ARTWORK",
    foreignKeys = [ForeignKey(
        entity = PodcastEntity::class,
        parentColumns = ["id"],
        childColumns = ["podcastIdFk"],
        onDelete = ForeignKey.CASCADE
    )]
)
internal data class PodcastArtworkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val url: String,
    val width: Int,
    val height: Int,
    val podcastIdFk: Long
)

@Entity(
    tableName = "PODCAST_GENRE",
    primaryKeys = ["podcastId", "genreId"],
    foreignKeys = [ForeignKey(
        entity = PodcastEntity::class,
        parentColumns = ["id"],
        childColumns = ["podcastId"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = GenreEntity::class,
        parentColumns = ["id"],
        childColumns = ["genreId"],
        onDelete = ForeignKey.CASCADE
    )]
)
internal data class PodcastAndGenreMapEntity(
    val podcastId: Long,
    val genreId: Long
)
