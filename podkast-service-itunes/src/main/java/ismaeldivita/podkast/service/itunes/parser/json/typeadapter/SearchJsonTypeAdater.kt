package ismaeldivita.podkast.service.itunes.parser.json.typeadapter

import com.squareup.moshi.FromJson
import ismaeldivita.podkast.service.itunes.model.ItunesPodcast
import ismaeldivita.podkast.service.itunes.parser.json.model.SearchJson

internal object SearchJsonTypeAdater {

    @FromJson
    fun fromJson(json: SearchJson): List<ItunesPodcast> = json.results.map(
        PodcastTypeAdapter::fromJson)

}
