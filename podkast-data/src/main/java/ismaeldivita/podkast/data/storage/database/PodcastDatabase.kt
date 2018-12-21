package ismaeldivita.podkast.data.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ismaeldivita.podkast.data.storage.database.dao.GenreDAO
import ismaeldivita.podkast.data.storage.database.dao.PodcastDAO
import ismaeldivita.podkast.data.storage.database.entity.*

@Database(
        entities = [
            PodcastEntity::class,
            PodcastArtworkEntity::class,
            PodcastAndGenreMapEntity::class,
            GenreEntity::class,
            SubGenreEntity::class
        ],
        version = 1,
        exportSchema = false
)
internal abstract class PodcastDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_FILE_NAME = "PODCAST"
    }

    abstract fun genreDAO(): GenreDAO

    abstract fun podcastDAO(): PodcastDAO

}