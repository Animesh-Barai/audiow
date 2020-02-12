package audiow.core.android.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    protected fun Disposable.registerDisposable() = compositeDisposable.add(this)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}