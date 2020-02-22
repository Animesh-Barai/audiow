package audiow.user.signin.ui.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import audiow.core.android.livedata.observe
import audiow.core.android.ui.activity.ViewModelActivity
import audiow.core.android.ui.activity.finishWithResult
import audiow.core.common.ApplicationProperties
import audiow.core.monitoring.log.Logger
import audiow.core.util.standart.exhaustive
import audiow.user.signin.R
import audiow.user.signin.databinding.UserFeatureSigninActivityBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import javax.inject.Inject

class SignInActivity : ViewModelActivity<SignInViewModel>() {

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<UserFeatureSigninActivityBinding>(
            this, R.layout.user_feature_signin_activity
        ).apply {
            vm = viewModel
        }

        observe(viewModel.action) { action ->
            when (action) {
                SignInAction.StartGoogleSignIn -> startGoogleSignIn()
                SignInAction.OnSignIn -> finishWithResult(RESULT_OK)
            }.exhaustive
        }
    }

    private fun startGoogleSignIn() {
        startActivityForResult(googleSignInClient.signInIntent, RC_GOOGLE_SIGN_IN)
    }

    private fun handleGoogleSignInResult(data: Intent?) = try {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val acc = requireNotNull(task.getResult(ApiException::class.java)) { "Account is null" }

        viewModel.onGoogleSignIn(acc)
    } catch (error: Throwable) {
        Logger.d("Google SignIn Intent Error", mapOf("message" to error.message))
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_GOOGLE_SIGN_IN -> handleGoogleSignInResult(data)
        }
    }

    companion object {
        private const val RC_GOOGLE_SIGN_IN = 1000

        fun getIntent(context: Context) = Intent(context, SignInActivity::class.java)
    }
}