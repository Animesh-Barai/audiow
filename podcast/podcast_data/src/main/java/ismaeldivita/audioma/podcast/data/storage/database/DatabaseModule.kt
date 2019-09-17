package ismaeldivita.audioma.podcast.data.storage.database

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ismaeldivita.audioma.podcast.data.storage.database.dao.feed.FeedGenreSectionDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.GenreDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.PodcastDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.feed.FeedBannerDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.feed.FeedHighlightDAO
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
    internal fun provideFeedGenreDao(database: PodcastDatabase): FeedGenreSectionDAO = database.feedGenreSection()

    @Provides
    @Singleton
    internal fun provideFeedBannerDao(database: PodcastDatabase): FeedBannerDAO = database.feedBanner()

    @Provides
    @Singleton
    internal fun provideFeedHighlightDao(database: PodcastDatabase): FeedHighlightDAO = database.feedHighlight()

}