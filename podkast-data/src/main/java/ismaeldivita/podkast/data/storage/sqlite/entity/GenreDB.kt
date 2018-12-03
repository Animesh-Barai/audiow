package ismaeldivita.podkast.data.storage.sqlite.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GENRE")
data class GenreDB(
        @PrimaryKey val id: Int,
        val name: String
)