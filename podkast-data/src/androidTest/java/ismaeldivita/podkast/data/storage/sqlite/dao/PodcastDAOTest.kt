package ismaeldivita.podkast.data.storage.sqlite.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import ismaeldivita.podkast.data.storage.sqlite.PodcastDatabase
import ismaeldivita.podkast.data.storage.sqlite.entity.GenreDB
import ismaeldivita.podkast.data.storage.sqlite.entity.PodcastWrapperDB
import org.junit.Assert.assertEquals
import org.junit.Test

class PodcastDAOTest {

    private val database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PodcastDatabase::class.java
    ).build()

    private val podcastDAO = database.podcastDAO()
    private val genreDAO = database.genreDAO()

    @Test
    fun writeThenRead() {
        val podcastWrapperDB = PodcastWrapperDB(
                PodcastWrapperDB.PodcastDB(2, "Title", "Artist", "rssUrl", false),
                listOf(
                        PodcastWrapperDB.PodcastArtworkDB(0, "url", 10, 10, 2),
                        PodcastWrapperDB.PodcastArtworkDB(0, "url", 11, 11, 2)
                ),
                emptyList()
        )
        podcastDAO.podcastWrapperTransaction(podcastWrapperDB)
        podcastDAO.getAll().blockingGet().first().run {
            assertEquals(podcastWrapperDB, this)
        }
    }

    @Test
    fun whenReplacePodcastShouldCleanArtworkList() {
        val initialArtwork = listOf(
                PodcastWrapperDB.PodcastArtworkDB(0, "url", 10, 10, 2),
                PodcastWrapperDB.PodcastArtworkDB(0, "url", 11, 11, 2)
        )
        val updatedArtwork = listOf(
                PodcastWrapperDB.PodcastArtworkDB(0, "url2", 10, 10, 2),
                PodcastWrapperDB.PodcastArtworkDB(0, "url2", 11, 11, 2)
        )
        val podcastWrapperDB = PodcastWrapperDB(
                PodcastWrapperDB.PodcastDB(2, "Title", "Artist", "rssUrl", false),
                emptyList(),
                emptyList()
        )

        fun insertPodcastAndAssertArtwork(artworkList: List<PodcastWrapperDB.PodcastArtworkDB>) {
            podcastDAO.podcastWrapperTransaction(podcastWrapperDB.copy(artworkList = artworkList))
            podcastDAO.getAll().blockingGet().first().run {
                assertEquals(podcastWrapperDB.copy(artworkList = artworkList), this)
            }
        }
        insertPodcastAndAssertArtwork(initialArtwork)
        insertPodcastAndAssertArtwork(updatedArtwork)
    }

    @Test(expected = SQLiteConstraintException::class)
    fun whenInsertWithoutGenreShouldCrashWithForeignKeyError() {
        val podcastWrapperDB = PodcastWrapperDB(
                PodcastWrapperDB.PodcastDB(2, "Title", "Artist", "rssUrl", false),
                emptyList(),
                listOf(1)
        )
        podcastDAO.podcastWrapperTransaction(podcastWrapperDB)
    }

    @Test
    fun writeThenReadWithGenre() {
        val podcastWrapperDB = PodcastWrapperDB(
                PodcastWrapperDB.PodcastDB(2, "Title", "Artist", "rssUrl", false),
                listOf(
                        PodcastWrapperDB.PodcastArtworkDB(0, "url", 10, 10, 2),
                        PodcastWrapperDB.PodcastArtworkDB(0, "url", 11, 11, 2)
                ),
                listOf(1)
        )
        genreDAO.insert(GenreDB(1, "Genre"))
        podcastDAO.podcastWrapperTransaction(podcastWrapperDB)
        podcastDAO.getAll().blockingGet().first().run {
            assertEquals(podcastWrapperDB, this)
        }
    }

}