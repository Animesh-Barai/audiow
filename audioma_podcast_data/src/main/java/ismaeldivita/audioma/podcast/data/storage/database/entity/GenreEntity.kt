package ismaeldivita.audioma.podcast.data.storage.database.entity

import androidx.room.*

@Entity(tableName = "GENRE")
internal data class GenreEntity(
        @PrimaryKey val id: Int,
        val name: String,
        val topPodcastsUrl: String
)

@Entity(tableName = "SUB_GENRE",
        primaryKeys = ["genreId", "subGenreId"],
        foreignKeys = [
            ForeignKey(entity = GenreEntity::class,
                    parentColumns = ["id"],
                    childColumns = ["genreId"],
                    onDelete = ForeignKey.CASCADE),
            ForeignKey(entity = GenreEntity::class,
                    parentColumns = ["id"],
                    childColumns = ["subGenreId"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
internal data class SubGenreEntity(
        val genreId: Int,
        val subGenreId: Int
)

internal data class GenreWithSubGenre(
        @Embedded
        val genre: GenreEntity,

        @Relation(
                parentColumn = "id",
                entityColumn = "genreId",
                entity = SubGenreEntity::class,
                projection = ["subGenreId"]
        )
        val subGenreIds: List<Int>
)