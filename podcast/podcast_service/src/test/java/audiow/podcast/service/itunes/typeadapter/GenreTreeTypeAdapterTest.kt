package audiow.podcast.service.itunes.typeadapter

import com.squareup.moshi.JsonReader
import audiow.core.util.standart.Tree
import audiow.podcast.service.itunes.model.ItunesGenre
import audiow.podcast.service.itunes.parser.json.typeadapter.GenreTreeTypeAdapter
import audiow.podcast.service.itunes.testhelper.IOUtils
import org.junit.Test

import org.junit.Assert.*

class GenreTreeTypeAdapterTest {

    private val adapter =
        GenreTreeTypeAdapter

    @Test
    fun fromJson() {
        val source = IOUtils.fileToBufferedSource("/json/genre/genre_short_tree.json")
        val jsonReader = JsonReader.of(source)
        val actual = adapter.fromJson(jsonReader)


        val root = Tree.Node(
            ItunesGenre(
                id = 26,
                name = "Podcasts",
                topPodcastsUrl = "https://itunes.apple.com/us/rss/toppodcasts/genre=26/json"
            )
        )

        val child1 = Tree.Node(
            ItunesGenre(
                id = 1301,
                name = "Arts",
                topPodcastsUrl = "https://itunes.apple.com/us/rss/" +
                        "toppodcasts/genre=1301/json"
            ),
            root
        )
        val child2 = Tree.Node(
            ItunesGenre(
                id = 1321, name = "Business",
                topPodcastsUrl = "https://itunes.apple.com/us/rss/" +
                        "toppodcasts/genre=1321/json"

            ),
            root
        )
        root.children.add(child1)
        root.children.add(child2)

        val expected = Tree(root)

        assertEquals(expected.toList().map { it.value }, actual.toList().map { it.value })
    }

}
