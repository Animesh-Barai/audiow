package ismaeldivita.audioma.podcast.data.interactor.genre

import io.reactivex.Single
import ismaeldivita.audioma.core.interactor.Interactor
import ismaeldivita.audioma.core.interactor.invoke
import ismaeldivita.audioma.core.monitoring.log.Logger
import ismaeldivita.audioma.podcast.data.model.Genre
import javax.inject.Inject

interface GetPreferredGenres : Interactor<Unit, Single<List<Genre>>>

internal class GetPreferredGenresImpl @Inject constructor(
    private val getGenreTree: GetGenreTree
) : GetPreferredGenres {

    /** This should based on user preferences but for now taking the first 4 leafs in the tree */
    override fun invoke(param: Unit): Single<List<Genre>> =
        getGenreTree().map { tree ->
            tree.filter { node -> node.children.isNotEmpty() }
                .map { it.value }
                .take(4)
        }.doOnSuccess { genres -> Logger.i(genres.map { it.name }.toString()) }
}
