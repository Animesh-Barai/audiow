package ismaeldivita.podkast.data.storage.sqlite.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import ismaeldivita.podkast.data.storage.sqlite.PodcastDatabase
import ismaeldivita.podkast.data.storage.sqlite.entity.GenreEntity
import ismaeldivita.podkast.data.storage.sqlite.entity.SubGenreEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class GenreDAOTest {

    private val genreDAO = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PodcastDatabase::class.java
    ).build().genreDAO()

    @Test
    fun writeThenRead() {
        val genre = GenreEntity(1, "Genre-Test", "http://test.com")
        genreDAO.insert(genre)
        assertEquals(genre, genreDAO.getAll().blockingGet().first())
    }

    @Test
    fun writeThenRead_withRelation() {
        val parent = GenreEntity(1, "Genre-Parent", "http://test.com")
        val child = GenreEntity(2, "Genre-Child", "http://test.com")

        genreDAO.insert(parent)
        genreDAO.insert(child)
        genreDAO.insertSubGenre(SubGenreEntity(parent.id, child.id))
        val subGenreId = genreDAO.getAllWithSubGenres()
                .blockingGet()
                .first { it.genre.id == parent.id }
                .subGenreIds.first()

        assertEquals(child.id, subGenreId)
    }

}