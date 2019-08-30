package ismaeldivita.audioma.app.screens.launch

import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import ismaeldivita.audioma.app.screens.main.PlayerActivity
import ismaeldivita.audioma.core.android.livedata.observe
import ismaeldivita.audioma.core.util.standart.exhaustive
import javax.inject.Inject

class LaunchActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: LaunchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.onCreate()

        observe(viewModel.state) {
            when (it) {
                LaunchState.Initialized -> {
                    finish()
                    startActivity(Intent(this, PlayerActivity::class.java))
                }
                LaunchState.Loading -> { }
                LaunchState.Error -> { }
            }.exhaustive
        }
    }

}