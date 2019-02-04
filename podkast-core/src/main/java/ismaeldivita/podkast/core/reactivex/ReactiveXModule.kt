package ismaeldivita.podkast.core.reactivex

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
internal class ReactiveXModule {

    @Provides
    @Singleton
    @GlobalCompositeDisposable
    fun provideGlobalCompositeDisposable() = CompositeDisposable()
}