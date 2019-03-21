package ismaeldivita.audioma.podcast.service.itunes.parser.json.typeadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import ismaeldivita.audioma.core.util.standart.Tree
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesGenre
import ismaeldivita.audioma.podcast.service.itunes.parser.json.MoshiProvider
import ismaeldivita.audioma.podcast.service.itunes.parser.json.model.GenreJson
import ismaeldivita.audioma.podcast.service.util.parse

internal object GenreTreeTypeAdapter {

    @FromJson
    fun fromJson(reader: JsonReader): Tree<ItunesGenre> = with(reader) {
        beginObject()
        nextName()
        val genreJson = MoshiProvider.instance.parse<GenreJson>(this)!!
        endObject()

        return Tree(root = genreJson.toItunesGenreNode(null))
    }

    private fun GenreJson.toItunesGenreNode(parent: Tree.Node<ItunesGenre>?): Tree.Node<ItunesGenre> =
        Tree.Node(value = toItunesGenre(), parent = parent)
            .apply {
                children.addAll(subgenres.toList().map { it.second.toItunesGenreNode(this) })
            }

    private fun GenreJson.toItunesGenre() = ItunesGenre(
        id,
        name,
        rssUrls.getValue("topPodcasts")
    )
}
