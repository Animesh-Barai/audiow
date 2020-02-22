package audiow.user.data.storage

import android.app.Application
import androidx.room.Room
import audiow.user.data.storage.dao.UserDAO
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

@Module
internal class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): UserDatabase = Room.databaseBuilder(
        application,
        UserDatabase::class.java,
        UserDatabase.DATABASE_FILE_NAME
    ).build()

    @Provides
    @Reusable
    internal fun provideGenreDao(database: UserDatabase): UserDAO = database.userDAO()
}