package ismaeldivita.podkast.service.parser.json.typeadapter

import com.squareup.moshi.JsonReader
import ismaeldivita.podkast.service.model.Artwork
import ismaeldivita.podkast.service.model.Genre
import ismaeldivita.podkast.service.model.Podcast
import ismaeldivita.podkast.service.testhelper.IOUtils
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PodcastTypeAdapterTest {

    @Test
    fun fromJson() {
        val source = IOUtils.fileToBufferedSource("/json/search/podcast_item.json")
        val jsonReader = JsonReader.of(source)
        val podcastJson = PodcastJsonTypeAdapter.fromJson(jsonReader)!!
        val actual = PodcastTypeAdapter.fromJson(podcastJson)
        val expected = getExpectedPodcast()

        assertEquals(expected, actual)
    }

    @Test
    fun fromJson_whenTrackExplicitness_isCleaned() {
        val source = IOUtils.fileToBufferedSource("/json/search/podcast_item.json")
        val jsonReader = JsonReader.of(source)
        val podcastJson = PodcastJsonTypeAdapter.fromJson(jsonReader)!!.copy(trackExplicitness = "cleaned")
        val expected = getExpectedPodcast().copy(explicit = false)
        val actual = PodcastTypeAdapter.fromJson(podcastJson)

        assertEquals(expected, actual)
    }

    @Test
    fun fromJson_whenTrackExplicitness_isNotExplicit() {
        val source = IOUtils.fileToBufferedSource("/json/search/podcast_item.json")
        val jsonReader  = JsonReader.of(source)
        val podcastJson = PodcastJsonTypeAdapter.fromJson(jsonReader)!!.copy(trackExplicitness = "notExplicit")
        val expected = getExpectedPodcast().copy(explicit = false)
        val actual = PodcastTypeAdapter.fromJson(podcastJson)

        assertEquals(expected, actual)
    }

    private fun getExpectedPodcast() = Podcast(
            id = 360084272,
            title = "The Joe Rogan Experience",
            artistName = "Joe Rogan",
            rssUrl = "http://joeroganexp.joerogan.libsynpro.com/rss",
            artworkList = listOf(
                    Artwork("https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/30x30bb.jpg", 30, 30),
                    Artwork("https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/60x60bb.jpg", 60, 60),
                    Artwork("https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/100x100bb.jpg", 100, 100),
                    Artwork("https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/600x600bb.jpg", 600, 600)
            ),
            primaryGenre = Genre(1303, "Comedy"),
            genreList = listOf(
                    Genre(1303, "Comedy"),
                    Genre(1450, "Podcasting"),
                    Genre(1324, "Society & Culture")
            ),
            explicit = true
    )

}
