package ismaeldivita.audioma.podcast.data.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ismaeldivita.audioma.podcast.data.storage.database.dao.feed.FeedGenreSectionDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.GenreDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.PodcastDAO
import ismaeldivita.audioma.podcast.data.storage.database.entity.*
import ismaeldivita.audioma.podcast.data.storage.database.entity.feed.*
import ismaeldivita.audioma.podcast.data.storage.database.entity.feed.FeedBannerEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.feed.FeedBannerPodcastsEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.feed.FeedGenreSectionPodcastsEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.feed.FeedHighlightEntity

@Database(
    entities = [
        PodcastEntity::class,
        PodcastArtworkEntity::class,
        PodcastAndGenreMapEntity::class,
        GenreEntity::class,
        SubGenreEntity::class,
        FeedBannerEntity::class,
        FeedBannerPodcastsEntity::class,
        FeedHighlightEntity::class,
        FeedGenreSectionEntity::class,
        FeedGenreSectionPodcastsEntity::class
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

    abstract fun feedGenreSection(): FeedGenreSectionDAO

}