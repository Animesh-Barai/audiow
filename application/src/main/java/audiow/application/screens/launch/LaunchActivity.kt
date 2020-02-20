package audiow.application.screens.launch

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import dagger.android.support.DaggerAppCompatActivity
import audiow.application.screens.main.HomeActivity
import audiow.core.android.livedata.observe
import audiow.core.android.viewmodel.ViewModelFactory
import audiow.core.util.standart.exhaustive
import audiow.user.signin.ui.signin.SignInActivity
import javax.inject.Inject

class LaunchActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var vmFactory: ViewModelFactory<LaunchViewModel>

    lateinit var viewModel: LaunchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, vmFactory).get()
        viewModel.onCreate()

        // TODO move to databinding
        observe(viewModel.state) {
            when (it) {
                LaunchState.Initialized.Home -> launchHome()

                LaunchState.Initialized.SignIn ->
                    startActivityForResult(SignInActivity.getIntent(this), RC_SIGN_IN)

                LaunchState.Loading,
                LaunchState.Error -> {
                }
            }.exhaustive
        }
    }

    private fun launchHome() {
        finish()
        startActivity(Intent(this, HomeActivity::class.java))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode to resultCode) {
            RC_SIGN_IN to RESULT_OK -> launchHome()
            RC_SIGN_IN to RESULT_CANCELED -> finish()
        }
    }

    companion object {
        private const val RC_SIGN_IN = 1000
    }
}