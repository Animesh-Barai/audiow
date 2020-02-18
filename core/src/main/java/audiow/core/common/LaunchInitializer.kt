package audiow.core.common

import io.reactivex.Completable

interface LaunchInitializer {

    fun initialize(): Completable

}