package ismaeldivita.podkast.service.parser.typeadapter

import com.squareup.moshi.Moshi
import ismaeldivita.podkast.service.model.internal.SearchJson
import ismaeldivita.podkast.service.testhelper.IOUtils
import ismaeldivita.podkast.service.util.adapter
import org.junit.Assert.*
import org.junit.Test

class SearchJsonTypeAdaterTest {
    private val adapter = SearchJsonTypeAdater()

    @Test
    fun fromJson() {
        val json = IOUtils.fileToString("/json/search/search.json")
        val searchJson = Moshi.Builder().build().adapter<SearchJson>().fromJson(json)!!
        val actual = adapter.fromJson(searchJson)
        val expected = searchJson.copy().results

        assertEquals(expected, actual)
    }
}
