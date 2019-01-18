package ismaeldivita.podkast.common.monitoring.log

import timber.log.Timber

object Logger {

    fun d(message: String) = Timber.d(message)

    fun i(message: String) = Timber.i(message)

    fun e(message: String) = Timber.e(message)

    fun e(throwable: Throwable) = Timber.e(throwable)

}