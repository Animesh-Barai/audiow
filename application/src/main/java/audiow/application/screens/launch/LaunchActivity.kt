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

        observe(viewModel.state) {
            when (it) {
                LaunchState.Initialized.Home -> {
                    finish()
                    startActivity(Intent(this, HomeActivity::class.java))
                }
                LaunchState.Initialized.SignIn -> {
                    finish()
                    startActivity(Intent(this, SignInActivity::class.java))
                }
                LaunchState.Loading -> { }
                LaunchState.Error -> { }
            }.exhaustive
        }
    }

}