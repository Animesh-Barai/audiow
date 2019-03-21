package ismaeldivita.noizu.podcast.service.itunes.typeadapter

import ismaeldivita.noizu.podcast.service.itunes.parser.json.model.SearchJson
import ismaeldivita.noizu.podcast.service.itunes.parser.json.MoshiProvider
import ismaeldivita.noizu.podcast.service.itunes.parser.json.typeadapter.PodcastTypeAdapter
import ismaeldivita.noizu.podcast.service.itunes.parser.json.typeadapter.SearchJsonTypeAdater
import ismaeldivita.noizu.podcast.service.itunes.testhelper.IOUtils
import ismaeldivita.noizu.podcast.service.itunes.util.adapter
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchJsonTypeAdaterTest {

    @Test
    fun fromJson() {
        val json = IOUtils.fileToString("/json/search/search.json")
        val searchJson = MoshiProvider.instanceWithAdapters.adapter<SearchJson>().fromJson(json)!!
        val actual = SearchJsonTypeAdater.fromJson(searchJson)
        val expected = PodcastTypeAdapter.fromJson(searchJson.copy().results)

        assertEquals(expected, actual)
    }

}
