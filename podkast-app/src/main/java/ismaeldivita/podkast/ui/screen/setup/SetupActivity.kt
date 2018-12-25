package ismaeldivita.podkast.ui.screen.setup

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import ismaeldivita.podkast.R
import javax.inject.Inject

class SetupActivity : DaggerAppCompatActivity() {

    @Inject lateinit var viewModel: SetupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)
    }

}