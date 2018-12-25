package ismaeldivita.podkast.data.interactor.setup

import io.reactivex.Single

interface DataSetupInteractor {

    fun isDataSetupCompleted(): Single<Boolean>

}