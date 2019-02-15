package ismaeldivita.podkast.service.parser.json.typeadapter

import com.squareup.moshi.JsonReader
import ismaeldivita.podkast.service.dto.ArtworkDTO
import ismaeldivita.podkast.service.dto.GenreDTO
import ismaeldivita.podkast.service.dto.PodcastDTO
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

    private fun getExpectedPodcast() = PodcastDTO(
            id = 360084272,
            title = "The Joe Rogan Experience",
            artistName = "Joe Rogan",
            rssUrl = "http://joeroganexp.joerogan.libsynpro.com/rss",
            artworkList = listOf(
                    ArtworkDTO("https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/30x30bb.jpg", 30, 30),
                    ArtworkDTO("https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/60x60bb.jpg", 60, 60),
                    ArtworkDTO("https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/100x100bb.jpg", 100, 100),
                    ArtworkDTO("https://is4-ssl.mzstatic.com/image/thumb/Music127/v4/d0/e6/5f/d0e65f81-c2cf-7f59-38e4-6abcfab7e38a/source/600x600bb.jpg", 600, 600)
            ),
            primaryGenre = GenreDTO(1303, "Comedy"),
            genreList = listOf(
                    GenreDTO(1303, "Comedy"),
                    GenreDTO(1450, "Podcasting"),
                    GenreDTO(1324, "Society & Culture")
            ),
            explicit = true
    )

}
