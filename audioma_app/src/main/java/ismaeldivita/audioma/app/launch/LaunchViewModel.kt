package ismaeldivita.audioma.app.launch

import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.subjects.PublishSubject
import ismaeldivita.audioma.core.android.LaunchInitializer
import ismaeldivita.audioma.core.android.livedata.liveDataOf
import ismaeldivita.audioma.core.android.viewmodel.BaseViewModel
import ismaeldivita.audioma.core.util.reactive.SchedulersProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LaunchViewModel @Inject constructor(
    private val initializers: Set<@JvmSuppressWildcards LaunchInitializer>,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    val state = liveDataOf<LaunchState>()

    private val retrySubject = PublishSubject.create<Unit>()

    fun onCreate() {
        initializeLaunchers()
    }

    fun onRetry() {
        retrySubject.onNext(Unit)
    }

    private fun initializeLaunchers() {
        Completable.merge(initializers.map { it.initialize() })
            .doOnSubscribe { state.postValue(LaunchState.Loading) }
            .doOnError { state.postValue(LaunchState.Error) }
            .retryWhen {
                retrySubject.toFlowable(BackpressureStrategy.DROP)
                    .throttleLatest(1, TimeUnit.SECONDS, schedulersProvider.computation())
            }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.main())
            .subscribe { state.value = LaunchState.Initialized }
            .registerDisposable()
    }

}