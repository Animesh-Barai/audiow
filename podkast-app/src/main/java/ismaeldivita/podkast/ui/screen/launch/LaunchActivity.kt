package ismaeldivita.podkast.ui.screen.launch

import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import ismaeldivita.podkast.MainActivity
import ismaeldivita.podkast.data.interactor.setup.DataSetupInteractor
import ismaeldivita.podkast.ui.screen.setup.SetupActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LaunchActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var setupInteractor: DataSetupInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupInteractor
                .isDataSetupCompleted()
                .delay(5, TimeUnit.SECONDS)
                .subscribe({
                    if (it) {
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        startActivity(Intent(this, SetupActivity::class.java))
                    }
                }, {})
    }
}