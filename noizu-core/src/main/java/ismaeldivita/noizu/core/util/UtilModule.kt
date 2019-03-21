package ismaeldivita.noizu.core.util

import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.disposables.CompositeDisposable
import ismaeldivita.noizu.core.util.reactivex.GlobalCompositeDisposable
import ismaeldivita.noizu.core.util.time.CurrentTimeProvider
import ismaeldivita.noizu.core.util.time.TimeProvider
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