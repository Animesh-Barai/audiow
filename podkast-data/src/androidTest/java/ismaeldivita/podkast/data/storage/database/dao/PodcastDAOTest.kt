package ismaeldivita.podkast.data.storage.database.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import ismaeldivita.podkast.data.storage.database.PodcastDatabase
import ismaeldivita.podkast.data.storage.database.entity.GenreEntity
import ismaeldivita.podkast.data.storage.database.entity.PodcastArtworkEntity
import ismaeldivita.podkast.data.storage.database.entity.PodcastEntity
import ismaeldivita.podkast.data.storage.database.entity.PodcastWrapperEntity
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
        val podcastWrapperDB = PodcastWrapperEntity(
                PodcastEntity(2, "Title", "Artist", "rssUrl", false),
                listOf(
                        PodcastArtworkEntity(0, "url", 10, 10, 2),
                        PodcastArtworkEntity(0, "url", 11, 11, 2)
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
                PodcastArtworkEntity(0, "url", 10, 10, 2),
                PodcastArtworkEntity(0, "url", 11, 11, 2)
        )
        val updatedArtwork = listOf(
                PodcastArtworkEntity(0, "url2", 10, 10, 2),
                PodcastArtworkEntity(0, "url2", 11, 11, 2)
        )
        val podcastWrapperDB = PodcastWrapperEntity(
                PodcastEntity(2, "Title", "Artist", "rssUrl", false),
                emptyList(),
                emptyList()
        )

        fun insertPodcastAndAssertArtwork(artworkList: List<PodcastArtworkEntity>) {
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
        val podcastWrapperDB = PodcastWrapperEntity(
                PodcastEntity(2, "Title", "Artist", "rssUrl", false),
                emptyList(),
                listOf(1)
        )
        podcastDAO.podcastWrapperTransaction(podcastWrapperDB)
    }

    @Test
    fun writeThenReadWithGenre() {
        val podcastWrapperDB = PodcastWrapperEntity(
                PodcastEntity(2, "Title", "Artist", "rssUrl", false),
                listOf(
                        PodcastArtworkEntity(0, "url", 10, 10, 2),
                        PodcastArtworkEntity(0, "url", 11, 11, 2)
                ),
                listOf(1)
        )
        genreDAO.upsert(GenreEntity(1, "GenreDTO", ""))
        podcastDAO.podcastWrapperTransaction(podcastWrapperDB)
        podcastDAO.getAll().blockingGet().first().run {
            assertEquals(podcastWrapperDB, this)
        }
    }

}