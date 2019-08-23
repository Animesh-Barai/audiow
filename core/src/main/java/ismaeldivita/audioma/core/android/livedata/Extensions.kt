package ismaeldivita.audioma.core.android.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.hadilq.liveevent.LiveEvent

fun <T> liveDataOf(default: T? = null): MutableLiveData<T> = MutableLiveData<T>()
    .apply { default?.let { value = it } }

fun <T> liveEventOf(default: T? = null): MutableLiveData<T> = LiveEvent<T>()
    .apply { default?.let { value = it } }

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, block: (T) -> Unit) {
    liveData.observe(this, Observer(block))
}
