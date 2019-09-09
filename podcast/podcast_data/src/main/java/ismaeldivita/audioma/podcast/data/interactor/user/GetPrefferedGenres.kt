package ismaeldivita.audioma.podcast.data.interactor.user

import io.reactivex.Single
import ismaeldivita.audioma.core.interactor.Interactor
import ismaeldivita.audioma.core.interactor.invoke
import ismaeldivita.audioma.core.monitoring.log.Logger
import ismaeldivita.audioma.podcast.data.interactor.genre.GetGenreTree
import ismaeldivita.audioma.podcast.data.model.Genre
import javax.inject.Inject

interface GetPreferredGenres : Interactor<Unit, Single<List<Genre>>>

internal class GetPreferredGenresImpl @Inject constructor(
    private val getGenreTree: GetGenreTree
) : GetPreferredGenres {

    /**
     * This should based on user preferences and usage but for now lets take the first
     * 4 leafs from the tree
     */
    override fun invoke(param: Unit): Single<List<Genre>> =
        getGenreTree().map { tree ->
            tree.filter { node -> node.children.isEmpty() }
                .map { it.value }
                .take(4)
        }.doOnSuccess { genres -> Logger.d(genres.map { it.name }.toString()) }
}
