package ismaeldivita.podkast.service.parser.typeadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ismaeldivita.podkast.service.model.Artwork
import ismaeldivita.podkast.service.model.internal.PodcastJson
import ismaeldivita.podkast.service.util.adapter

internal class PodcastJsonTypeAdapter {

    private val moshi by lazy { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }

    @FromJson
    fun fromJson(json: String): PodcastJson? =
            moshi.adapter<PodcastJson>()
                    .fromJson(json)
                    ?.copy(artworkList = parseArtworkList(json))

    private fun parseArtworkList(json: String): List<Artwork> {
        val jsonMap = moshi.adapter<Map<String, Any>>().fromJson(json)

        return jsonMap
                ?.filter { it.key.startsWith("artworkUrl", ignoreCase = true) }
                ?.map {
                    val size = it.key.split(Regex("(?=\\d*$)"), 2).component2().toInt()
                    Artwork(it.value as String, size, size)
                }
                ?: emptyList()
    }

}
