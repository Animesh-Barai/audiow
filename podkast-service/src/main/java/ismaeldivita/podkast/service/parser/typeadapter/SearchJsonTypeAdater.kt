package ismaeldivita.podkast.service.parser.typeadapter

import com.squareup.moshi.FromJson
import ismaeldivita.podkast.service.model.internal.PodcastJson
import ismaeldivita.podkast.service.model.internal.SearchJson

internal class SearchJsonTypeAdater {

    @FromJson
    fun fromJson(json: SearchJson): List<PodcastJson> = json.results

}
