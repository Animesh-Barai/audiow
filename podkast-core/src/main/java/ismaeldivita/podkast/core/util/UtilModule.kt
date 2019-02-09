package ismaeldivita.podkast.core.util

import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.disposables.CompositeDisposable
import ismaeldivita.podkast.core.util.reactivex.GlobalCompositeDisposable
import ismaeldivita.podkast.core.util.time.CurrentTimeProvider
import ismaeldivita.podkast.core.util.time.TimeProvider
import javax.inject.Singleton

@Module
class UtilModule {

    @Provides
    @Reusable
    internal fun bindTimeProvider(timeProvider: CurrentTimeProvider): TimeProvider = timeProvider

    @Provides
    @Singleton
    @GlobalCompositeDisposable
    fun provideGlobalCompositeDisposable() = CompositeDisposable()

}