package audiow.podcast.service.itunes.typeadapter

import audiow.podcast.service.itunes.parser.json.model.RootResultJson
import audiow.podcast.service.itunes.parser.json.MoshiProvider
import audiow.podcast.service.itunes.parser.json.typeadapter.PodcastTypeAdapter
import audiow.podcast.service.itunes.parser.json.typeadapter.SearchJsonTypeAdater
import audiow.podcast.service.itunes.testhelper.IOUtils
import audiow.podcast.service.util.adapter
import org.junit.Assert.assertEquals
import org.junit.Test

class RootResultJsonTypeAdaterTest {

    @Test
    fun fromJson() {
        val json = IOUtils.fileToString("/json/search/search.json")
        val searchJson = MoshiProvider.instanceWithAdapters.adapter<RootResultJson>().fromJson(json)!!
        val actual = SearchJsonTypeAdater.fromJson(searchJson)
        val expected = PodcastTypeAdapter.fromJson(searchJson.copy().results)

        assertEquals(expected, actual)
    }

}
