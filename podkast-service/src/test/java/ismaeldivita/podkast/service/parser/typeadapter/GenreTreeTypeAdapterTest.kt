package ismaeldivita.podkast.service.parser.typeadapter

import com.squareup.moshi.JsonReader
import ismaeldivita.podkast.service.model.Genre
import ismaeldivita.podkast.service.model.GenreNode
import ismaeldivita.podkast.service.model.GenreTree
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
        val expected = GenreTree(root = GenreNode(
                genre = Genre(26, "Podcasts"),
                subgenres = listOf(
                        GenreNode(
                                genre = Genre(1301, "Arts"),
                                subgenres = emptyList(),
                                topPodcastsUrl = "https://itunes.apple.com/us/rss/toppodcasts/" +
                                        "genre=1301/json"
                        ),
                        GenreNode(
                                genre = Genre(1321, "Business"),
                                subgenres = emptyList(),
                                topPodcastsUrl = "https://itunes.apple.com/us/rss/toppodcasts/" +
                                        "genre=1321/json"
                        )
                ),
                topPodcastsUrl = "https://itunes.apple.com/us/rss/toppodcasts/genre=26/json"
        ))
        assertEquals(expected, actual)
    }

}
