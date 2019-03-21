package ismaeldivita.noizu

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.schedulers.Schedulers
import ismaeldivita.noizu.podcast.R
import ismaeldivita.noizu.podcast.data.repository.Repository
import ismaeldivita.noizu.podcast.service.itunes.model.ItunesGenre
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var repository: Repository<ItunesGenre>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository.getAll()
                .subscribeOn(Schedulers.io())
                .subscribe({}, {})

    }

}
