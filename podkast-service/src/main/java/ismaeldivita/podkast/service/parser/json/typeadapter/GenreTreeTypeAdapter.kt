package ismaeldivita.podkast.service.parser.json.typeadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import ismaeldivita.podkast.service.model.Genre
import ismaeldivita.podkast.service.model.GenreDetail
import ismaeldivita.podkast.service.model.GenreTree
import ismaeldivita.podkast.service.parser.json.MoshiProvider
import ismaeldivita.podkast.service.parser.json.model.GenreDetailJson
import ismaeldivita.podkast.service.util.parse

internal object GenreTreeTypeAdapter {

    @FromJson
    fun fromJson(reader: JsonReader): GenreTree = with(reader) {
        beginObject()
        nextName()
        val node = MoshiProvider.instance.parse<GenreDetailJson>(this)!!
        endObject()

        GenreTree(root = node.toGenre())
    }

    @FromJson
    fun fromJson(json: GenreDetailJson): GenreDetail = with(json) {
        GenreDetail(
                subgenres = subgenres.map { it.value.toGenre() },
                topPodcastsUrl = rssUrls.getValue("topPodcasts")
        )
    }

    private fun GenreDetailJson.toGenre() = Genre(id, name, fromJson(this))


}
