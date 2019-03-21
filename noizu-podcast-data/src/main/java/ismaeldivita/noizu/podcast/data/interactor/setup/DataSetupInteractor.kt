package ismaeldivita.noizu.podcast.data.interactor.setup

import io.reactivex.Single

interface DataSetupInteractor {

    fun isDataSetupCompleted(): Single<Boolean>

}