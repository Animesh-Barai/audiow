package audiow.podcast.data.storage.database.entity

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
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PodcastArtworkEntity

        if (url != other.url) return false
        if (width != other.width) return false
        if (height != other.height) return false
        if (podcastIdFk != other.podcastIdFk) return false

        return true
    }

    override fun hashCode(): Int {
        var result = url.hashCode()
        result = 31 * result + width
        result = 31 * result + height
        result = 31 * result + podcastIdFk.hashCode()
        return result
    }
}

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
