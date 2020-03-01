package audiow.podcast.service.itunes.parser.json.typeadapter

import com.squareup.moshi.FromJson
import audiow.podcast.service.itunes.model.ItunesPodcast
import audiow.podcast.service.itunes.parser.json.model.RootResultJson

internal object SearchJsonTypeAdater {

    @FromJson
    fun fromJson(json: RootResultJson): List<ItunesPodcast> {
        return json
            .results
            .mapNotNull(PodcastTypeAdapter::fromJson)
    }

    @FromJson
    fun findById(json: RootResultJson): ItunesPodcast {
        return json
            .results
            .first()
            .let { PodcastTypeAdapter.fromJson(it)!! }
    }
}
