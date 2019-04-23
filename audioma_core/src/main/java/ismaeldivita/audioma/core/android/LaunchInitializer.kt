package ismaeldivita.audioma.core.android

import io.reactivex.Completable

interface LaunchInitializer {

    fun initialize(): Completable

}