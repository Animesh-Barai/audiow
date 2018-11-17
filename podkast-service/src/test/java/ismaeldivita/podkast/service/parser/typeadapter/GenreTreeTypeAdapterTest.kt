package ismaeldivita.podkast.service.parser.typeadapter

import com.squareup.moshi.JsonReader
import ismaeldivita.podkast.service.model.Genre
import ismaeldivita.podkast.service.model.GenreDetail
import ismaeldivita.podkast.service.model.GenreTree
import ismaeldivita.podkast.service.parser.json.typeadapter.GenreTreeTypeAdapter
import ismaeldivita.podkast.service.testhelper.IOUtils
import org.junit.Test

import org.junit.Assert.*

class GenreTreeTypeAdapterTest {

    private val adapter = GenreTreeTypeAdapter

    @Test
    fun fromJson() {
        val source = IOUtils.fileToBufferedSource("/json/genre/genre_short_tree.json")
        val jsonReader = JsonReader.of(source)
        val actual = adapter.fromJson(jsonReader)
        val expected = GenreTree(
                Genre(id = 26, name = "Podcasts", detail = GenreDetail(
                        subgenres = listOf(
                                Genre(id = 1301, name = "Arts", detail = GenreDetail(
                                        subgenres = emptyList(),
                                        topPodcastsUrl = "https://itunes.apple.com/us/rss/" +
                                                "toppodcasts/genre=1301/json"
                                )),
                                Genre(id = 1321, name = "Business", detail = GenreDetail(
                                        subgenres = emptyList(),
                                        topPodcastsUrl = "https://itunes.apple.com/us/rss/" +
                                                "toppodcasts/genre=1321/json"))
                        ),
                        topPodcastsUrl = "https://itunes.apple.com/us/rss/toppodcasts/genre=26/json")
                )
        )

        assertEquals(expected, actual)
    }

}
