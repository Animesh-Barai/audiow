package ismaeldivita.podkast.service.parser.json.typeadapter

import com.squareup.moshi.FromJson
import ismaeldivita.podkast.service.model.Podcast
import ismaeldivita.podkast.service.parser.json.model.SearchJson

internal object SearchJsonTypeAdater {

    @FromJson
    fun fromJson(json: SearchJson): List<Podcast> = json.results.map(PodcastTypeAdapter::fromJson)

}
