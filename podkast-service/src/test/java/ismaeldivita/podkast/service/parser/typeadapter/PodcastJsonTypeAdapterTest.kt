package ismaeldivita.podkast.service.parser.typeadapter

import ismaeldivita.podkast.service.model.Artwork
import ismaeldivita.podkast.service.model.internal.PodcastJson
import ismaeldivita.podkast.service.testhelper.IOUtils
import junit.framework.Assert.assertEquals
import org.junit.Test

class PodcastJsonTypeAdapterTest {

    private val adapter = PodcastJsonTypeAdapter()

    @Test
    fun fromJson() {
        val json = IOUtils.fileToString("/json/search/podcast_item.json")
        val actual = adapter.fromJson(json)
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
                contentAdvisoryRating = "Explicit",
                genreIds = listOf(1303, 26, 1450, 1324),
                genres = listOf("Comedy", "Podcasts", "Podcasting", "Society & Culture"),
                artworkList = listOf(
                        Artwork("https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/30x30bb.jpg", 30, 30),
                        Artwork("https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/60x60bb.jpg", 60, 60),
                        Artwork("https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/100x100bb.jpg", 100, 100),
                        Artwork("https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/600x600bb.jpg", 600, 600)
                )
        )
        assertEquals(expected, actual)
    }

}
