package ismaeldivita.audioma.podcast.service.itunes.parser.json.typeadapter

import com.squareup.moshi.FromJson
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesPodcast
import ismaeldivita.audioma.podcast.service.itunes.parser.json.model.SearchJson

internal object SearchJsonTypeAdater {

    @FromJson
    fun fromJson(json: SearchJson): List<ItunesPodcast> = json.results.map(
        PodcastTypeAdapter::fromJson)

}
