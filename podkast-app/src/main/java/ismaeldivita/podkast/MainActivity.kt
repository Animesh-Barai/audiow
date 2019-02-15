package ismaeldivita.podkast

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.schedulers.Schedulers
import ismaeldivita.podkast.data.repository.Repository
import ismaeldivita.podkast.service.dto.GenreDTO
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var repository: Repository<GenreDTO>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository.getAll()
                .subscribeOn(Schedulers.io())
                .subscribe({}, {})

    }

}
