package ismaeldivita.audioma.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ismaeldivita.audioma.application.di.viewmodel.ViewModelFactory
import ismaeldivita.audioma.application.di.viewmodel.ViewModelKey
import ismaeldivita.audioma.ui.screen.setup.SetupViewModel

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SetupViewModel::class)
    fun postListViewModel(viewModel: SetupViewModel): ViewModel

}