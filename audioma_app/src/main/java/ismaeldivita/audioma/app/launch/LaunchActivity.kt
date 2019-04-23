package ismaeldivita.audioma.app.launch

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
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
                LaunchState.Initialized -> {}
                LaunchState.Loading -> {}
                LaunchState.Error -> {}
            }.exhaustive
        }
    }

}