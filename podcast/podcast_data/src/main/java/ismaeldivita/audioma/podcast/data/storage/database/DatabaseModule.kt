package ismaeldivita.audioma.podcast.data.storage.database

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ismaeldivita.audioma.podcast.data.storage.database.dao.feed.FeedGenreSectionDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.GenreDAO
import ismaeldivita.audioma.podcast.data.storage.database.dao.PodcastDAO
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
    internal fun provideFeedDao(database: PodcastDatabase): FeedGenreSectionDAO = database.feedGenreSection()

}