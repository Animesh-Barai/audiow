package ismaeldivita.audioma.podcast.data.interactor.setup

import io.reactivex.Single
import ismaeldivita.audioma.podcast.data.storage.database.dao.GenreDAO
import javax.inject.Inject

internal class DataSetupInteractorImpl @Inject constructor(
        private val genreDAO: GenreDAO
) : DataSetupInteractor {

    override fun isDataSetupCompleted(): Single<Boolean> =
            genreDAO.getAll()
                    .map { !it.isEmpty() }
                    .onErrorReturnItem(false)

}