package ismaeldivita.podkast.data.storage.sqlite.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import ismaeldivita.podkast.data.storage.sqlite.PodcastDatabase
import ismaeldivita.podkast.data.storage.sqlite.entity.GenreDB
import junit.framework.Assert.assertEquals
import org.junit.Test

class GenreDAOTest {

    private val genreDAO = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PodcastDatabase::class.java
    ).build().genreDAO()

    @Test
    fun writeThenRead() {
        val genre = GenreDB(1, "Genre-Test")
        genreDAO.insert(genre)
        assertEquals(genre, genreDAO.getAll().blockingGet().first())
    }

}