package ismaeldivita.podkast.data.storage.sqlite.entity

import androidx.room.*

internal data class PodcastWrapperDB(
        @Embedded val podcast: PodcastDB,

        @Relation(parentColumn = "id", entityColumn = "podcastIdFk")
        val artworkList: List<PodcastArtworkDB>,

        @Relation(
                parentColumn = "id",
                entityColumn = "podcastId",
                entity = PodcastGenreDB::class,
                projection = ["genreId"]
        )
        val genreIds: List<Int>
) {

    @Entity(tableName = "PODCAST")
    internal data class PodcastDB(
            @PrimaryKey val id: Int,
            val title: String,
            val artistName: String,
            val rssUrl: String,
            val explicit: Boolean
    )

    @Entity(tableName = "PODCAST_ARTWORK",
            foreignKeys = [(ForeignKey(
                    entity = PodcastDB::class,
                    parentColumns = ["id"],
                    childColumns = ["podcastIdFk"],
                    onDelete = ForeignKey.CASCADE
            ))])
    internal data class PodcastArtworkDB(
            @PrimaryKey(autoGenerate = true) val id: Int,
            val url: String,
            val width: Int,
            val height: Int,
            val podcastIdFk: Int
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as PodcastArtworkDB

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
            result = 31 * result + podcastIdFk
            return result
        }
    }

    @Entity(
            tableName = "PODCAST_GENRE",
            primaryKeys = ["podcastId", "genreId"],
            foreignKeys = [
                ForeignKey(entity = PodcastDB::class,
                        parentColumns = ["id"],
                        childColumns = ["podcastId"],
                        onDelete = ForeignKey.CASCADE
                ),
                ForeignKey(entity = GenreDB::class,
                        parentColumns = ["id"],
                        childColumns = ["genreId"],
                        onDelete = ForeignKey.CASCADE
                )
            ]
    )
    internal data class PodcastGenreDB(
            val podcastId: Int,
            val genreId: Int
    )

}