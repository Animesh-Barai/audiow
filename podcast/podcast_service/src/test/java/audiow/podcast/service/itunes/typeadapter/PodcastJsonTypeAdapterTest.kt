package audiow.podcast.service.itunes.typeadapter

import com.squareup.moshi.JsonReader
import audiow.podcast.service.itunes.model.ItunesArtwork
import audiow.podcast.service.itunes.parser.json.model.PodcastJson
import audiow.podcast.service.itunes.parser.json.typeadapter.PodcastJsonTypeAdapter
import audiow.podcast.service.itunes.testhelper.IOUtils
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PodcastJsonTypeAdapterTest {

    private val adapter =
        PodcastJsonTypeAdapter

    @Test
    fun fromJson() {
        val source = IOUtils.fileToBufferedSource("/json/search/podcast_item.json")
        val jsonReader = JsonReader.of(source)
        val actual = adapter.fromJson(jsonReader)
        val expected = PodcastJson(
            wrapperType = "track",
            kind = "podcast",
            artistId = 974891224,
            collectionId = 360084272,
            trackId = 360084272,
            artistName = "Joe Rogan",
            collectionName = "The Joe Rogan Experience",
            trackName = "The Joe Rogan Experience",
            collectionCensoredName = "The Joe Rogan Experience",
            trackCensoredName = "The Joe Rogan Experience",
            feedUrl = "http://joeroganexp.joerogan.libsynpro.com/rss",
            releaseDate = "2018-07-28T00:16:00Z",
            collectionExplicitness = "explicit",
            trackExplicitness = "explicit",
            trackCount = 300,
            country = "USA",
            primaryGenreName = "Comedy",
            genreIds = listOf(1303, 26, 1450, 1324),
            genres = listOf("Comedy", "Podcasts", "Podcasting", "Society & Culture"),
            artworkList = listOf(
                ItunesArtwork(
                    "https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/30x30bb.jpg",
                    30,
                    30
                ),
                ItunesArtwork(
                    "https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/60x60bb.jpg",
                    60,
                    60
                ),
                ItunesArtwork(
                    "https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/100x100bb.jpg",
                    100,
                    100
                ),
                ItunesArtwork(
                    "https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/600x600bb.jpg",
                    600,
                    600
                )
            )
        )
        assertEquals(expected, actual)
    }

}
