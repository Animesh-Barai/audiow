package audiow.podcast.data.interactor.user

import io.reactivex.Single
import audiow.core.interactor.Interactor
import audiow.core.interactor.invoke
import audiow.core.monitoring.log.Logger
import audiow.podcast.data.interactor.genre.GetGenreTree
import audiow.podcast.data.model.Genre
import javax.inject.Inject

/**
 *  Return a list of genre order by the user preference and usage
 */
interface GetPreferredGenres : Interactor<Unit, Single<List<Genre>>>

internal class GetPreferredGenresImpl @Inject constructor(
    private val getGenreTree: GetGenreTree
) : GetPreferredGenres {

    /**
     * This should be based on user preferences and usage but for now lets take random
     * leafs from the tree
     */
    override fun invoke(param: Unit): Single<List<Genre>> =
        getGenreTree().map { tree ->
            tree.filter { node -> node.children.isEmpty() }
                .map { it.value }
                .shuffled()
        }.doOnSuccess { genres -> Logger.d(genres.map { it.name }.toString()) }
}
