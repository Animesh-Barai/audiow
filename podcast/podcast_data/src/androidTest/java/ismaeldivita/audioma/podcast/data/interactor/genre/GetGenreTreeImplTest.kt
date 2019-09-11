package ismaeldivita.audioma.podcast.data.interactor.genre

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.interactor.invoke
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.storage.database.dao.GenreDAO
import ismaeldivita.audioma.podcast.data.storage.database.entity.GenreEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.GenreWithSubGenre
import ismaeldivita.audioma.podcast.data.util.TestSchedulerProvider
import org.junit.Test

class GetGenreTreeImplTest {

    private val dao = mock<GenreDAO>()
    private val repository = mock<Repository<Genre>>()
    private val scheduler = TestSchedulerProvider()
    private val interactor: GetGenreTree = GetGenreTreeImpl(dao, repository, scheduler)

    @Test
    fun given_connected_nodes_should_build_the_tree() {
        givenGenreList(1, 2, 3, 4)
        givenRelation(
            1 to listOf(2, 3),
            2 to emptyList(),
            3 to listOf(4),
            4 to emptyList()
        )

        interactor()
            .test()
            .assertValue { it.count() == 4 }
    }

    @Test
    fun given_disconnected_nodes_should_be_ignored() {
        givenGenreList(1, 2, 3, 4)
        givenRelation(
            1 to listOf(2, 3),
            2 to emptyList(),
            3 to emptyList()
        )

        interactor()
            .test()
            .assertValue { it.count() == 3 }
    }

    @Test
    fun given_two_root_nodes_should_fail() {
        givenGenreList(1, 2, 3, 4)
        givenRelation(1 to listOf(2), 3 to listOf(4), 4 to emptyList())

        interactor()
            .test()
            .assertError(GetGenreTreeImpl.MultipleRootsFoundException)
    }

    @Test
    fun given_none_root_node_should_fail() {
        givenGenreList()
        givenRelation()

        interactor()
            .test()
            .assertError(GetGenreTreeImpl.RootNotFoundException)
    }

    private fun givenGenreList(vararg ids: Int) = ids.map { Genre(it, "$it") }
        .let { whenever(repository.getAll()).doReturn(Single.just(it)) }

    private fun givenRelation(vararg relations: Pair<Int, List<Int>>) =
        relations.map {
            GenreWithSubGenre(
                genre = GenreEntity(id = it.first, name = ""),
                subGenreIds = it.second
            )
        }.let { whenever(dao.getAllWithSubGenres()).doReturn(Single.just(it)) }
}