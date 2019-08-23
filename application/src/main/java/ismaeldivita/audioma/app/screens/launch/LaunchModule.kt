package ismaeldivita.audioma.app.screens.launch

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ismaeldivita.audioma.core.android.viewmodel.ViewModelKey

@Module
abstract class LaunchModule {

    @Binds
    @IntoMap
    @ViewModelKey(LaunchViewModel::class)
    abstract fun postListViewModel(viewModel: LaunchViewModel): ViewModel

}