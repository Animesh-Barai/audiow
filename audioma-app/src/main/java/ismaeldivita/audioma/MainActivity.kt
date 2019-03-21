package ismaeldivita.audioma

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.schedulers.Schedulers
import ismaeldivita.audioma.core.monitoring.log.Logger
import ismaeldivita.audioma.podcast.R
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.repository.Repository
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var repository: Repository<Genre>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository.getAll()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Logger.d(it.toString())
                }, {})

    }

}
