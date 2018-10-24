package ismaeldivita.podkast.service.parser.json.typeadapter

import com.squareup.moshi.FromJson
import ismaeldivita.podkast.service.model.PodcastSketch
import ismaeldivita.podkast.service.parser.json.typeadapter.model.SearchJson

internal object SearchJsonTypeAdater {

    @FromJson
    fun fromJson(json: SearchJson): List<PodcastSketch> = json.results.map(PodcastTypeAdapter::fromJson)

}
