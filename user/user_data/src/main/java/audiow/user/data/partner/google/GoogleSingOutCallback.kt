package audiow.user.data.partner.google

import audiow.core.common.SignOutCallback
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import io.reactivex.Completable
import javax.inject.Inject

class GoogleSingOutCallback @Inject constructor(
    private val googleSignInClient: GoogleSignInClient
) : SignOutCallback {

    override fun onSignOut() = Completable.fromCallable {
        googleSignInClient.signOut()
    }
}