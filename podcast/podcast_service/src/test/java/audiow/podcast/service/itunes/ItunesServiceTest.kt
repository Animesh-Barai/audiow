package audiow.podcast.service.itunes

import audiow.podcast.service.itunes.testhelper.IOUtils
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test

class ItunesServiceTest {

    private val mockWebServer = MockWebServer()
    private val service = ItunesService.build(baseUrl = mockWebServer.url("").toString())

    @After
    fun tearDown() {
        mockWebServer.close()
    }

    @Test
    fun search() {
        val searchResponse = IOUtils.fileToString("/json/search/search_50.json")
        mockWebServer.enqueue(MockResponse().setBody(searchResponse))

        service.search()
            .test()
            .assertValue { it.size == 50 }
    }

    @Test
    fun getGenreTree() {
        val genreResponse = IOUtils.fileToString("/json/genre/genre_68.json")
        mockWebServer.enqueue(MockResponse().setBody(genreResponse))

        service.getGenreTree()
            .test()
            .assertValue { it.count() == 68 }
    }

    @Test
    fun getEpisodes() {
        val episodeResponse = IOUtils.fileToString("/xml/feed_10.xml")
        mockWebServer.enqueue(MockResponse().setBody(episodeResponse))
        service.getPodcastRss("")
            .test()
            .assertValue { it.body()!!.episodes.size == 10 }
    }

}
