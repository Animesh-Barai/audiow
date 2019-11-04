package ismaeldivita.audioma.app.screens.main

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import dagger.android.support.DaggerAppCompatActivity
import ismaeldivita.audioma.app.R
import ismaeldivita.audioma.podcast.feature.discover.PocastFragmentProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    internal lateinit var transactionProvider: Provider<FragmentTransaction>

    @Inject
    internal lateinit var podcastFragmentProvider: PocastFragmentProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_menu.setOnItemSelectedListener { menuId ->
            val fragment = when (menuId) {
                R.id.menu_discover -> podcastFragmentProvider.discover()
                else -> null
            }

            fragment?.let {
                transactionProvider.get()
                    .replace(R.id.main_container, it)
                    .commit()
            }
        }
    }

}