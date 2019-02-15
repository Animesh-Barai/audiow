package ismaeldivita.podkast.service.parser.json.typeadapter

import com.squareup.moshi.FromJson
import ismaeldivita.podkast.service.dto.PodcastDTO
import ismaeldivita.podkast.service.parser.json.model.SearchJson

internal object SearchJsonTypeAdater {

    @FromJson
    fun fromJson(json: SearchJson): List<PodcastDTO> = json.results.map(PodcastTypeAdapter::fromJson)

}
