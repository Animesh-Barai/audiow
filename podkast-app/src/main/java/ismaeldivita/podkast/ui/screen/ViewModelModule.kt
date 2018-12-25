package ismaeldivita.podkast.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ismaeldivita.podkast.application.di.viewmodel.ViewModelFactory
import ismaeldivita.podkast.application.di.viewmodel.ViewModelKey
import ismaeldivita.podkast.ui.screen.setup.SetupViewModel

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SetupViewModel::class)
    internal abstract fun postListViewModel(viewModel: SetupViewModel): ViewModel

}