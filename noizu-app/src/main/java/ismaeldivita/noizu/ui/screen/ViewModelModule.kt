package ismaeldivita.noizu.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ismaeldivita.noizu.application.di.viewmodel.ViewModelFactory
import ismaeldivita.noizu.application.di.viewmodel.ViewModelKey
import ismaeldivita.noizu.ui.screen.setup.SetupViewModel

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SetupViewModel::class)
    fun postListViewModel(viewModel: SetupViewModel): ViewModel

}