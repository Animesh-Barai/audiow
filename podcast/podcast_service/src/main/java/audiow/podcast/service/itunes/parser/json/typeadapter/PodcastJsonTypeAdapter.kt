package audiow.podcast.service.itunes.parser.json.typeadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import audiow.podcast.service.itunes.model.ItunesArtwork
import audiow.podcast.service.itunes.parser.json.MoshiProvider
import audiow.podcast.service.itunes.parser.json.model.PodcastJson
import audiow.podcast.service.util.adapter

internal object PodcastJsonTypeAdapter {

    @FromJson
    fun fromJson(reader: JsonReader): PodcastJson {
        @Suppress("UNCHECKED_CAST")
        val jsonMap = reader.readJsonValue() as Map<String, Any>

        return MoshiProvider.instance
            .adapter<PodcastJson>()
            .fromJsonValue(jsonMap)!!
            .copy(
                artworkList = parseArtworkList(
                    jsonMap
                )
            )
    }

    private fun parseArtworkList(jsonMap: Map<String, *>): List<ItunesArtwork> {
        return jsonMap
            .filter { it.key.startsWith("artworkUrl", ignoreCase = true) }
            .map {
                val size = it.key.split(Regex("(?=\\d*$)"), 2).component2().toInt()
                ItunesArtwork(
                    it.value as String,
                    size,
                    size
                )
            }
    }

}
