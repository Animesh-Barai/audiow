package audiow.user.data.partner.google

import android.app.Application
import audiow.core.common.ApplicationProperties
import audiow.core.common.SignOutCallback
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoSet

@Module
abstract class GoogleModule {

    @Module
    companion object {

        @Provides
        @Reusable
        @JvmStatic
        fun provideGoogleSignInClient(
            application: Application,
            appProperties: ApplicationProperties
        ) = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(appProperties.googleWebClientId)
            .requestEmail()
            .build()
            .let { GoogleSignIn.getClient(application, it) }
    }

    @Binds
    @IntoSet
    abstract fun bindGoogleSignOutCallback(callback: GoogleSingOutCallback): SignOutCallback
}