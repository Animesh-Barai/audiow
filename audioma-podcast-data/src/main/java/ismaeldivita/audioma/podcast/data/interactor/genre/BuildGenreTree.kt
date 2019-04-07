package ismaeldivita.audioma.podcast.data.interactor.genre

import io.reactivex.Single
import ismaeldivita.audioma.core.interactor.Interactor
import ismaeldivita.audioma.core.util.standart.Tree
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.storage.database.dao.GenreDAO
import ismaeldivita.audioma.podcast.data.storage.database.entity.GenreWithSubGenre
import javax.inject.Inject

internal class BuildGenreTree @Inject constructor(
    val genreDAO: GenreDAO
) : Interactor<List<Genre>, Single<Tree<Genre>>> {

    override fun invoke(params: List<Genre>): Single<Tree<Genre>> =
        genreDAO.getAllWithSubGenres().map { buildTree(params, it) }

    private fun buildTree(genreList: List<Genre>, relation: List<GenreWithSubGenre>): Tree<Genre> {
        val genreIds = genreList.map { it.id }
        val filteredRelation = relation.filter { genreIds.contains(it.genre.id) }
        val rootGenre = findRoot(filteredRelation).let { root ->
            genreList.first { it.id == root.genre.id }
        }

        fun Genre.toNode(parent: Tree.Node<Genre>?): Tree.Node<Genre> {
            val subgenres = filteredRelation
                .first { it.genre.id == id }
                .subGenreIds
                .map { childId -> genreList.first { it.id == childId } }

            return Tree.Node(value = this, parent = parent)
                .apply { children.addAll(subgenres.map { it.toNode(this) }) }
        }

        return Tree(rootGenre.toNode(null))
    }

    private fun findRoot(relations: List<GenreWithSubGenre>): GenreWithSubGenre {
        val children = relations.map { it.subGenreIds }.flatten().toSet()
        val roots = relations.filter { !children.contains(it.genre.id) }

        return when {
            roots.isEmpty() -> throw RootNotFoundException
            roots.size > 1 -> throw MultipleRootsFoundException
            else -> roots.first()
        }
    }

    object RootNotFoundException : IllegalArgumentException()
    object MultipleRootsFoundException : IllegalArgumentException()

}