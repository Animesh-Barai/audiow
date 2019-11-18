package ismaeldivita.audioma.core.util

import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.disposables.CompositeDisposable
import ismaeldivita.audioma.core.util.reactive.GlobalCompositeDisposable
import ismaeldivita.audioma.core.util.reactive.ReactiveModule
import ismaeldivita.audioma.core.util.time.CurrentTimeProvider
import ismaeldivita.audioma.core.util.time.TimeModule
import ismaeldivita.audioma.core.util.time.TimeProvider
import javax.inject.Singleton

@Module(includes = [TimeModule::class, ReactiveModule::class])
internal class UtilModule