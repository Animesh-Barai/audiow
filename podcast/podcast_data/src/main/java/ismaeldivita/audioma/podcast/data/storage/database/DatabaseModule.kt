package ismaeldivita.audioma.podcast.data.storage.database

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ismaeldivita.audioma.podcast.data.storage.database.dao.FeedDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.discover.DiscoverGenreSectionDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.GenreDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.PodcastDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.discover.DiscoverBannerDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.discover.DiscoverHighlightDAO
import javax.inject.Singleton

@Module
internal class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): PodcastDatabase = Room.databaseBuilder(
            application,
            PodcastDatabase::class.java,
            PodcastDatabase.DATABASE_FILE_NAME
    ).build()

    @Provides
    @Singleton
    internal fun provideGenreDao(database: PodcastDatabase): GenreDAO = database.genreDAO()

    @Provides
    @Singleton
    internal fun providePodcastDao(database: PodcastDatabase): PodcastDAO = database.podcastDAO()

    @Provides
    @Singleton
    internal fun provideDiscoverGenreDao(database: PodcastDatabase): DiscoverGenreSectionDAO = database.feedGenreSection()

    @Provides
    @Singleton
    internal fun provideDiscoverBannerDao(database: PodcastDatabase): DiscoverBannerDAO = database.discoverBanner()

    @Provides
    @Singleton
    internal fun provideDiscoverHighlightDao(database: PodcastDatabase): DiscoverHighlightDAO = database.feedHighlight()

    @Provides
    @Singleton
    internal fun provideFeedDao(database: PodcastDatabase): FeedDAO = database.feedDAO()

}