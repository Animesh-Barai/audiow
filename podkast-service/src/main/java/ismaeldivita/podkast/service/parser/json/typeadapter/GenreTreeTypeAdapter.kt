package ismaeldivita.podkast.service.parser.json.typeadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import ismaeldivita.podkast.service.dto.GenreDTO
import ismaeldivita.podkast.service.dto.GenreDetailDTO
import ismaeldivita.podkast.service.dto.GenreDTOTree
import ismaeldivita.podkast.service.parser.json.MoshiProvider
import ismaeldivita.podkast.service.parser.json.model.GenreDetailJson
import ismaeldivita.podkast.service.util.parse

internal object GenreTreeTypeAdapter {

    @FromJson
    fun fromJson(reader: JsonReader): GenreDTOTree = with(reader) {
        beginObject()
        nextName()
        val node = MoshiProvider.instance.parse<GenreDetailJson>(this)!!
        endObject()

        GenreDTOTree(root = node.toGenre())
    }

    @FromJson
    fun fromJson(json: GenreDetailJson): GenreDetailDTO = with(json) {
        GenreDetailDTO(
                subgenres = subgenres.map { it.value.toGenre() },
                topPodcastsUrl = rssUrls.getValue("topPodcasts")
        )
    }

    private fun GenreDetailJson.toGenre() = GenreDTO(id, name, fromJson(this))


}
