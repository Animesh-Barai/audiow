package ismaeldivita.audioma.core.android

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ismaeldivita.audioma.core.android.viewmodel.ViewModelFactory

@Module
abstract class AndroidModule {

    @Binds
    internal abstract fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}