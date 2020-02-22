package audiow.podcast.data.storage.database

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import audiow.podcast.data.storage.database.dao.FeedDAO
import audiow.podcast.data.storage.database.dao.discover.DiscoverGenreSectionDAO
import audiow.podcast.data.storage.database.dao.GenreDAO
import audiow.podcast.data.storage.database.dao.PodcastDAO
import audiow.podcast.data.storage.database.dao.discover.DiscoverBannerDAO
import audiow.podcast.data.storage.database.dao.discover.DiscoverHighlightDAO
import dagger.Reusable
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
    @Reusable
    internal fun provideGenreDao(database: PodcastDatabase): GenreDAO = database.genreDAO()

    @Provides
    @Reusable
    internal fun providePodcastDao(database: PodcastDatabase): PodcastDAO = database.podcastDAO()

    @Provides
    @Reusable
    internal fun provideDiscoverGenreDao(database: PodcastDatabase): DiscoverGenreSectionDAO = database.feedGenreSection()

    @Provides
    @Reusable
    internal fun provideDiscoverBannerDao(database: PodcastDatabase): DiscoverBannerDAO = database.discoverBanner()

    @Provides
    @Reusable
    internal fun provideDiscoverHighlightDao(database: PodcastDatabase): DiscoverHighlightDAO = database.feedHighlight()

    @Provides
    @Reusable
    internal fun provideFeedDao(database: PodcastDatabase): FeedDAO = database.feedDAO()
}