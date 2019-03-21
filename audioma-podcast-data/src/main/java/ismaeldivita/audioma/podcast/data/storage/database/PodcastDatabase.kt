package ismaeldivita.audioma.podcast.data.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ismaeldivita.audioma.podcast.data.storage.database.dao.GenreDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.PodcastDAO
import ismaeldivita.audioma.podcast.data.storage.database.entity.*

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