package ismaeldivita.audioma.app.screens.main

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import dagger.android.support.DaggerAppCompatActivity
import ismaeldivita.audioma.app.R
import ismaeldivita.audioma.core.android.ui.FragmentTransactor
import ismaeldivita.audioma.podcast.feature.discover.PocastFragmentProvider
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject
import javax.inject.Provider

class HomeActivity : DaggerAppCompatActivity() {

    @Inject
    internal lateinit var fragmentTransactor: FragmentTransactor

    @Inject
    internal lateinit var podcastFragmentProvider: PocastFragmentProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        main_menu.setOnItemSelectedListener { menuId ->
            val fragment = when (menuId) {
                R.id.menu_discover -> podcastFragmentProvider.discover()
                else -> null
            }
            fragment?.let {
                fragmentTransactor.replace(it).commit()
            }
        }
    }

}