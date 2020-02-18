package audiow.core.common

import io.reactivex.Completable

interface SignOutCallback {

    fun onSignOut(): Completable

}