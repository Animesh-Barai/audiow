package ismaeldivita.podkast.data.interactor.setup

import io.reactivex.Single
import ismaeldivita.podkast.data.storage.database.dao.GenreDAO
import javax.inject.Inject

internal class DataSetupInteractorImpl @Inject constructor(
        private val genreDAO: GenreDAO
) : DataSetupInteractor {

    override fun isDataSetupCompleted(): Single<Boolean> =
            genreDAO.getAll()
                    .map { !it.isEmpty() }
                    .onErrorReturnItem(false)

}