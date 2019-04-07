package ismaeldivita.audioma.podcast.data.interactor.setup

import androidx.test.filters.LargeTest
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import ismaeldivita.audioma.podcast.data.interactor.genre.BuildGenreTree
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.storage.database.dao.GenreDAO
import ismaeldivita.audioma.podcast.data.storage.database.entity.GenreEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.GenreWithSubGenre
import org.junit.Test

class BuildGenreTreeTest {

    private val dao = mock<GenreDAO>()
    private val interactor = BuildGenreTree(dao)

    @Test
    @LargeTest
    fun repeat(){
        repeat(1000) { given_connected_nodes_should_build_the_tree() }
    }

    @Test
    fun given_connected_nodes_should_build_the_tree() {
        val genres = generateGenre(1, 2, 3, 4)
        val relation = generateGenreRelation(
            1 to listOf(2, 3),
            2 to emptyList(),
            3 to listOf(4),
            4 to emptyList()
        )

        whenever(dao.getAllWithSubGenres()).doReturn(Single.just(relation))

        interactor(genres)
            .test()
            .assertValue { it.count() == 4 }
    }

    @Test
    fun given_disconnected_nodes_should_be_ignored() {
        val genres = generateGenre(1, 2, 3, 4)
        val relation = generateGenreRelation(
            1 to listOf(2, 3),
            2 to emptyList(),
            3 to emptyList()
        )

        whenever(dao.getAllWithSubGenres()).doReturn(Single.just(relation))

        interactor(genres)
            .test()
            .assertValue { it.count() == 3 }
    }

    @Test
    fun given_two_root_nodes_should_fail() {
        val genres = generateGenre(1, 2, 3, 4)
        val relation = generateGenreRelation(1 to listOf(2), 3 to listOf(4), 4 to emptyList())

        whenever(dao.getAllWithSubGenres()).doReturn(Single.just(relation))

        interactor(genres)
            .test()
            .assertError(BuildGenreTree.MultipleRootsFoundException)
    }

    @Test
    fun given_none_root_node_should_fail() {
        val genres = generateGenre()
        val relation = generateGenreRelation()

        whenever(dao.getAllWithSubGenres()).doReturn(Single.just(relation))

        interactor(genres)
            .test()
            .assertError(BuildGenreTree.RootNotFoundException)
    }

    private fun generateGenre(vararg ids: Int) = ids.map { Genre(it, "$it", "") }

    private fun generateGenreRelation(vararg relations: Pair<Int, List<Int>>) =
        relations.map {
            GenreWithSubGenre(
                genre = GenreEntity(id = it.first, name = "", topPodcastsUrl = ""),
                subGenreIds = it.second
            )
        }
}