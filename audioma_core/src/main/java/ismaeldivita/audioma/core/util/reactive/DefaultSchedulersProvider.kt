package ismaeldivita.audioma.core.util.reactive

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

internal class DefaultSchedulersProvider @Inject constructor() : SchedulersProvider {

    override fun main() = AndroidSchedulers.mainThread()

    override fun io() = Schedulers.io()

    override fun computation() = Schedulers.computation()

    override fun newThread() = Schedulers.newThread()

}