package ismaeldivita.podkast.service.parser.json.typeadapter

import ismaeldivita.podkast.service.parser.json.model.SearchJson
import ismaeldivita.podkast.service.parser.json.MoshiProvider
import ismaeldivita.podkast.service.testhelper.IOUtils
import ismaeldivita.podkast.service.util.adapter
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
