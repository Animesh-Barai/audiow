package audiow.podcast.data.storage.database.dao.discover

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import audiow.podcast.data.storage.database.PodcastDatabase
import audiow.podcast.data.storage.database.entity.*
import audiow.podcast.data.storage.database.entity.discover.DiscoverGenreSectionEntity
import audiow.podcast.data.storage.database.entity.discover.DiscoverGenreSectionPodcastsEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class FeedGenreSectionDAOTest {

    private val database = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        PodcastDatabase::class.java
    ).build()

    private val genreDAO = database.genreDAO()
    private val podcastDAO = database.podcastDAO()
    private val genreSectionDAO = database.feedGenreSection()

    @Test
    fun write_then_read() {
        val genre = GenreEntity(1, "ItunesGenre-Test")
        val podcast = PodcastWrapperEntity(
            PodcastEntity(2, "Title", "Artist", 1, "rssUrl", false),
            listOf(
                PodcastArtworkEntity(0, "url", 10, 10, 2),
                PodcastArtworkEntity(0, "url", 11, 11, 2)
            ),
            listOf(1)
        )
        val genreSectionFeed = mapOf(
            DiscoverGenreSectionEntity(
                1,
                1
            ) to listOf(
                DiscoverGenreSectionPodcastsEntity(
                    2,
                    1
                )
            )
        )
        genreDAO.upsert(genre)
        podcastDAO.upsertPodcastWrapperTransaction(podcast)
        genreSectionDAO.insertGenreSections(genreSectionFeed)

        val section = genreSectionDAO.getAllGenreSections().blockingGet()

        assertEquals(genre.id, section.first().section.genreId)
        assertEquals(podcast.podcast.id, section.first().podcasts.first().podcastId)
    }

}