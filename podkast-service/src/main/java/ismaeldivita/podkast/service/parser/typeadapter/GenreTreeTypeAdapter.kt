package ismaeldivita.podkast.service.parser.typeadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import ismaeldivita.podkast.service.model.Genre
import ismaeldivita.podkast.service.model.GenreNode
import ismaeldivita.podkast.service.model.GenreTree
import ismaeldivita.podkast.service.parser.typeadapter.model.GenreNodeJson
import ismaeldivita.podkast.service.parser.MoshiProvider
import ismaeldivita.podkast.service.util.parse

internal object GenreTreeTypeAdapter {

    @FromJson
    fun fromJson(reader: JsonReader): GenreTree = with(reader){
        beginObject()
        nextName()
        val node = MoshiProvider.instance.parse<GenreNodeJson>(this)!!
        endObject()

        GenreTree(root = fromJson(node))
    }

    @FromJson
    fun fromJson(json: GenreNodeJson): GenreNode = with(json) {
        GenreNode(
                genre = Genre(id, name),
                subgenres = subgenres.map { fromJson(it.value) },
                topPodcastsUrl = rssUrls.getValue("topPodcasts")
        )
    }

}
