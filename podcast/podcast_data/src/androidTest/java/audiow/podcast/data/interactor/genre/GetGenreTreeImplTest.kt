package audiow.podcast.data.interactor.genre

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import audiow.core.data.repository.Repository
import audiow.core.interactor.invoke
import audiow.podcast.data.model.Genre
import audiow.podcast.data.storage.database.dao.GenreDAO
import audiow.podcast.data.storage.database.entity.GenreEntity
import audiow.podcast.data.storage.database.entity.GenreWithSubGenre
import org.junit.Test

class GetGenreTreeImplTest {

    private val dao = mock<GenreDAO>()
    private val repository = mock<Repository<Genre>>()
    private val interactor: GetGenreTree = GetGenreTreeImpl(dao, repository)

    @Test
    fun given_connected_nodes_should_build_the_tree() {
        givenGenreList(1, 2, 3, 4)
        givenRelation(
            1L to listOf(2L, 3L),
            2L to emptyList(),
            3L to listOf(4L),
            4L to emptyList()
        )

        interactor()
            .test()
            .assertValue { it.count() == 4 }
    }

    @Test
    fun given_disconnected_nodes_should_be_ignored() {
        givenGenreList(1, 2, 3, 4)
        givenRelation(
            1L to listOf(2L, 3L),
            2L to emptyList(),
            3L to emptyList()
        )

        interactor()
            .test()
            .assertValue { it.count() == 3 }
    }

    @Test
    fun given_two_root_nodes_should_fail() {
        givenGenreList(1, 2, 3, 4)
        givenRelation(1L to listOf(2L), 3L to listOf(4L), 4L to emptyList())

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

    private fun givenGenreList(vararg ids: Long) = ids.map { Genre(it, "$it") }
        .let { whenever(repository.getAll()).doReturn(Single.just(it)) }

    private fun givenRelation(vararg relations: Pair<Long, List<Long>>) =
        relations.map {
            GenreWithSubGenre(
                genre = GenreEntity(id = it.first, name = ""),
                subGenreIds = it.second
            )
        }.let { whenever(dao.getAllWithSubGenres()).doReturn(Single.just(it)) }
}