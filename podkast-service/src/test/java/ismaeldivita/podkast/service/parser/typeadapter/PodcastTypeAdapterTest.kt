package ismaeldivita.podkast.service.parser

import ismaeldivita.podkast.service.model.Artwork
import ismaeldivita.podkast.service.model.Genre
import ismaeldivita.podkast.service.model.Podcast
import ismaeldivita.podkast.service.parser.typeadapter.PodcastJsonTypeAdapter
import ismaeldivita.podkast.service.parser.typeadapter.PodcastTypeAdapter
import ismaeldivita.podkast.service.testhelper.IOUtils
import junit.framework.Assert.assertEquals
import org.junit.Test

class PodcastTypeAdapterTest {

    private val adapter = PodcastTypeAdapter()
    private val jsonAdapter = PodcastJsonTypeAdapter()

    @Test
    fun fromJson() {
        val json = IOUtils.fileToString("/json/search/podcast_item.json")
        val podcastJson = jsonAdapter.fromJson(json)!!
        val actual = adapter.fromJson(podcastJson)
        val expected = getExpectedPodcast()

        assertEquals(expected, actual)
    }

    @Test
    fun fromJson_whenTrackExplicitness_isCleaned() {
        val json = IOUtils.fileToString("/json/search/podcast_item.json")
        val podcastJson = jsonAdapter.fromJson(json)!!.copy(trackExplicitness = "cleaned")
        val expected = getExpectedPodcast().copy(explicit = false)
        val actual = adapter.fromJson(podcastJson)

        assertEquals(expected, actual)
    }

    @Test
    fun fromJson_whenTrackExplicitness_isNotExplicit() {
        val json = IOUtils.fileToString("/json/search/podcast_item.json")
        val podcastJson = jsonAdapter.fromJson(json)!!.copy(trackExplicitness = "notExplicit")
        val expected = getExpectedPodcast().copy(explicit = false)
        val actual = adapter.fromJson(podcastJson)

        assertEquals(expected, actual)
    }

    private fun getExpectedPodcast() = Podcast(
            title = "The Joe Rogan Experience",
            artistName = "Joe Rogan",
            feedUrl = "http://joeroganexp.joerogan.libsynpro.com/rss",
            artworkList = listOf(
                    Artwork("https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/30x30bb.jpg", 30, 30),
                    Artwork("https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/60x60bb.jpg", 60, 60),
                    Artwork("https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/100x100bb.jpg", 100, 100),
                    Artwork("https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/600x600bb.jpg", 600, 600)
            ),
            primaryGenre = Genre(1303, "Comedy", null),
            genreList = listOf(
                    Genre(1303, "Comedy", null),
                    Genre(1450, "Podcasting", null),
                    Genre(1324, "Society & Culture", null)
            ),
            explicit = true
    )

}
