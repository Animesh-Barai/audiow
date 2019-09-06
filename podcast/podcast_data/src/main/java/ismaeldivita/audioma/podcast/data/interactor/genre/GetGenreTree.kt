package ismaeldivita.audioma.podcast.data.interactor.genre

import io.reactivex.Single
import ismaeldivita.audioma.core.interactor.Interactor
import ismaeldivita.audioma.core.util.standart.Tree
import ismaeldivita.audioma.podcast.data.model.Genre

interface GetGenreTree : Interactor<Unit, Single<Tree<Genre>>>