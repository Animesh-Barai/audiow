package audiow.application.screens.launch

import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.subjects.PublishSubject
import audiow.core.common.LaunchInitializer
import audiow.core.android.livedata.liveDataOf
import audiow.core.android.viewmodel.BaseViewModel
import audiow.core.interactor.invoke
import audiow.core.util.reactive.scheduler.SchedulersProvider
import audiow.user.data.interactor.IsUserLoggedIn
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LaunchViewModel @Inject constructor(
    private val initializers: Set<@JvmSuppressWildcards LaunchInitializer>,
    private val isUserLoggedIn: IsUserLoggedIn,
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
            .andThen(isUserLoggedIn())
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.main())
            .subscribe { isUserLoggedIn ->
                state.value = if (isUserLoggedIn) {
                    LaunchState.Initialized.Home
                } else {
                    LaunchState.Initialized.SignIn
                }
            }
            .registerDisposable()
    }

}