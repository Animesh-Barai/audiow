package ismaeldivita.noizu.podcast.data.interactor

import dagger.Binds
import dagger.Module
import dagger.Reusable
import ismaeldivita.noizu.podcast.data.interactor.setup.DataSetupInteractor
import ismaeldivita.noizu.podcast.data.interactor.setup.DataSetupInteractorImpl

@Module
internal abstract class InteractorModule {

    @Binds
    @Reusable
    abstract fun dataSetup(interactor: DataSetupInteractorImpl): DataSetupInteractor
}