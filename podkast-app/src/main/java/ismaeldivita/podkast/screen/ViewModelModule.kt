package ismaeldivita.podkast.screen

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ismaeldivita.podkast.application.di.viewmodel.ViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

//    @Binds
//    @IntoMap
//    @ViewModelKey(PostListViewModel::class)
//    internal abstract fun postListViewModel(viewModel: PostListViewModel): ViewModel

}