package audiow.podcast.data.storage.database.entity

import androidx.room.*

@Entity(tableName = "GENRE")
internal data class GenreEntity(
        @PrimaryKey val id: Long,
        val name: String
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
        val genreId: Long,
        val subGenreId: Long
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
        val subGenreIds: List<Long>
)