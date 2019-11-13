package ismaeldivita.audioma.podcast.data.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ismaeldivita.audioma.podcast.data.storage.database.dao.FeedDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.discover.DiscoverGenreSectionDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.GenreDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.PodcastDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.discover.DiscoverBannerDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.discover.DiscoverHighlightDAO
import ismaeldivita.audioma.podcast.data.storage.database.entity.*
import ismaeldivita.audioma.podcast.data.storage.database.entity.discover.*
import ismaeldivita.audioma.podcast.data.storage.database.entity.discover.DiscoverBannerEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.discover.DiscoverBannerPodcastEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.discover.DiscoverGenreSectionPodcastsEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.discover.DiscoverHighlightEntity

@Database(
    entities = [
        PodcastEntity::class,
        PodcastArtworkEntity::class,
        PodcastAndGenreMapEntity::class,
        FeedEntity::class,
        FeedEpisodeEntity::class,
        GenreEntity::class,
        SubGenreEntity::class,
        DiscoverBannerEntity::class,
        DiscoverBannerPodcastEntity::class,
        DiscoverHighlightEntity::class,
        DiscoverGenreSectionEntity::class,
        DiscoverGenreSectionPodcastsEntity::class
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

    abstract fun feedGenreSection(): DiscoverGenreSectionDAO

    abstract fun feedHighlight(): DiscoverHighlightDAO

    abstract fun discoverBanner(): DiscoverBannerDAO

    abstract fun feedDAO(): FeedDAO

}