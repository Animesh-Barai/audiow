package ismaeldivita.podkast.service.parser.json.typeadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import ismaeldivita.podkast.service.dto.ArtworkDTO
import ismaeldivita.podkast.service.parser.json.MoshiProvider
import ismaeldivita.podkast.service.parser.json.model.PodcastJson
import ismaeldivita.podkast.service.util.adapter

internal object PodcastJsonTypeAdapter {

    @FromJson
    fun fromJson(reader: JsonReader): PodcastJson {
        @Suppress("UNCHECKED_CAST")
        val jsonMap = reader.readJsonValue() as Map<String, Any>

        return MoshiProvider.instance
                .adapter<PodcastJson>()
                .fromJsonValue(jsonMap)!!
                .copy(artworkList = parseArtworkList(jsonMap))
    }

    private fun parseArtworkList(jsonMap: Map<String, *>): List<ArtworkDTO> {
        return jsonMap
                .filter { it.key.startsWith("artworkUrl", ignoreCase = true) }
                .map {
                    val size = it.key.split(Regex("(?=\\d*$)"), 2).component2().toInt()
                    ArtworkDTO(it.value as String, size, size)
                }
    }

}
