package ismaeldivita.audioma.podcast.data.storage.database.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import ismaeldivita.audioma.podcast.data.storage.database.PodcastDatabase
import ismaeldivita.audioma.podcast.data.storage.database.entity.GenreEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastArtworkEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastWrapperEntity
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
    fun write_then_read() {
        val podcastWrapperDB = PodcastWrapperEntity(
            PodcastEntity(2, "Title", "Artist", 1, "rssUrl", false),
            listOf(
                PodcastArtworkEntity(0, "url", 10, 10, 2),
                PodcastArtworkEntity(0, "url", 11, 11, 2)
            ),
            emptyList()
        )
        genreDAO.upsert(GenreEntity(1, "ItunesGenre", ""))
        podcastDAO.podcastWrapperTransaction(podcastWrapperDB)
        podcastDAO.getAll().blockingGet().first().run {
            assertEquals(podcastWrapperDB, this)
        }
    }

    @Test
    fun when_replace_podcast_should_clean_ArtworkList() {
        val initialArtwork = listOf(
            PodcastArtworkEntity(0, "url", 10, 10, 2),
            PodcastArtworkEntity(0, "url", 11, 11, 2)
        )
        val updatedArtwork = listOf(
            PodcastArtworkEntity(0, "url2", 10, 10, 2),
            PodcastArtworkEntity(0, "url2", 11, 11, 2)
        )
        val podcastWrapperDB = PodcastWrapperEntity(
            PodcastEntity(2, "Title", "Artist", 1, "rssUrl", false),
            emptyList(),
            emptyList()
        )

        fun insertPodcastAndAssertArtwork(artworkList: List<PodcastArtworkEntity>) {
            podcastDAO.podcastWrapperTransaction(podcastWrapperDB.copy(artworkList = artworkList))
            podcastDAO.getAll().blockingGet().first().run {
                assertEquals(podcastWrapperDB.copy(artworkList = artworkList), this)
            }
        }
        genreDAO.upsert(GenreEntity(1, "ItunesGenre", ""))
        insertPodcastAndAssertArtwork(initialArtwork)
        insertPodcastAndAssertArtwork(updatedArtwork)
    }

    @Test(expected = SQLiteConstraintException::class)
    fun when_insert_without_genre_should_crash_with_ForeignKeyError() {
        val podcastWrapperDB = PodcastWrapperEntity(
            PodcastEntity(2, "Title", "Artist", 1, "rssUrl", false),
            emptyList(),
            listOf(1)
        )
        podcastDAO.podcastWrapperTransaction(podcastWrapperDB)
    }

    @Test
    fun write_then_read_with_Genre() {
        val podcastWrapperDB = PodcastWrapperEntity(
            PodcastEntity(2, "Title", "Artist", 1, "rssUrl", false),
            listOf(
                PodcastArtworkEntity(0, "url", 10, 10, 2),
                PodcastArtworkEntity(0, "url", 11, 11, 2)
            ),
            listOf(1)
        )
        genreDAO.upsert(GenreEntity(1, "ItunesGenre", ""))
        podcastDAO.podcastWrapperTransaction(podcastWrapperDB)
        podcastDAO.getAll().blockingGet().first().run {
            assertEquals(podcastWrapperDB, this)
        }
    }

}