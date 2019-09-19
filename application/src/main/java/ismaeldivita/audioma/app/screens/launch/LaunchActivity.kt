package ismaeldivita.audioma.app.screens.launch

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import dagger.android.support.DaggerAppCompatActivity
import ismaeldivita.audioma.app.screens.main.MainActivity
import ismaeldivita.audioma.core.android.livedata.observe
import ismaeldivita.audioma.core.android.viewmodel.ViewModelFactory
import ismaeldivita.audioma.core.util.standart.exhaustive
import ismaeldivita.audioma.podcast.feature.ui.discover.PodcastDiscoverViewModel
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
                LaunchState.Initialized -> {
                    finish()
                    startActivity(Intent(this, MainActivity::class.java))
                }
                LaunchState.Loading -> { }
                LaunchState.Error -> { }
            }.exhaustive
        }
    }

}