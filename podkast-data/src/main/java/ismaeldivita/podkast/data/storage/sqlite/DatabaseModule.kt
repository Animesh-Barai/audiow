package ismaeldivita.podkast.data.storage.sqlite

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ismaeldivita.podkast.data.storage.sqlite.dao.GenreDAO
import ismaeldivita.podkast.data.storage.sqlite.dao.PodcastDAO
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

}