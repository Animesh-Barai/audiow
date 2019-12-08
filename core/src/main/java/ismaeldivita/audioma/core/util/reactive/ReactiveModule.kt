package ismaeldivita.audioma.core.util.reactive

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import io.reactivex.disposables.CompositeDisposable
import ismaeldivita.audioma.core.android.ApplicationInitializer
import ismaeldivita.audioma.core.util.reactive.scheduler.DefaultSchedulersProvider
import ismaeldivita.audioma.core.util.reactive.scheduler.SchedulersProvider
import javax.inject.Singleton

@Module(includes = [ReactiveModule.ReactiveBindingModule::class])
internal class ReactiveModule {

    @Provides
    @Singleton
    @GlobalCompositeDisposable
    fun provideGlobalCompositeDisposable() = CompositeDisposable()

    @Module
    internal abstract class ReactiveBindingModule {

        @Binds
        @IntoSet
        abstract fun reactiveInitializer(
            initializer: ReactiveInitializer
        ): ApplicationInitializer

        @Binds
        abstract fun schedulerProvider(
            schedulersProvider: DefaultSchedulersProvider
        ): SchedulersProvider

    }

}

