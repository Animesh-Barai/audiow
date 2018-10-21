package ismaeldivita.podkast.service

import ismaeldivita.podkast.service.testhelper.IOUtils
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test

class PodcastServiceTest {

    private val mockWebServer = MockWebServer()
    private val service = PodcastService.build { baseUrl = mockWebServer.url("").toString() }

    @Test
    fun search() {
        val searchResponse = IOUtils.fileToString("/json/search/search_50.json")
        mockWebServer.enqueue(MockResponse().setBody(searchResponse))

        service.search()
                .test()
                .assertValue { it.size == 50 }
                .assertNoErrors()
    }

    @Test
    fun getGenreTree() {
        val genreResponse = IOUtils.fileToString("/json/genre/genre_26.json")
        mockWebServer.enqueue(MockResponse().setBody(genreResponse))

        service.getGenreTree()
                .test()
                .assertValue {
                    true
                }
                .assertNoErrors()

    }
}