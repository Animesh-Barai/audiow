package ismaeldivita.audioma.podcast.data.storage.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import ismaeldivita.audioma.podcast.data.storage.database.PodcastDatabase
import ismaeldivita.audioma.podcast.data.storage.database.entity.GenreEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.SubGenreEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class GenreDAOTest {

    private val genreDAO = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PodcastDatabase::class.java
    ).build().genreDAO()

    @Test
    fun writeThenRead() {
        val genre = GenreEntity(1, "ItunesGenre-Test", "http://test.com")
        genreDAO.upsert(genre)
        assertEquals(genre, genreDAO.getAll().blockingGet().first())
    }

    @Test
    fun writeThenRead_withRelation() {
        val parent = GenreEntity(1, "ItunesGenre-Parent", "http://test.com")
        val child = GenreEntity(2, "ItunesGenre-Child", "http://test.com")
        val subGenreEntity = SubGenreEntity(parent.id, child.id)

        genreDAO.genreTransaction(listOf(parent, child), listOf(subGenreEntity))

        val subGenreId = genreDAO.getAllWithSubGenres()
                .blockingGet()
                .first { it.genre.id == parent.id }
                .subGenreIds.first()

        assertEquals(child.id, subGenreId)
    }

}