package audiow.application.screens.main

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import audiow.app.R
import audiow.core.android.ui.FragmentTransactor
import audiow.design.metrics.applySystemWindowsDesign
import audiow.podcast.feature.discover.PodcastDiscoverFragmentFactory
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity() {

    @Inject
    internal lateinit var fragmentTransactor: FragmentTransactor

    @Inject
    internal lateinit var podcastFragmentProvider: PodcastDiscoverFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        with(main_menu) {
            setOnItemSelectedListener { menuId ->
                val fragment = when (menuId) {
                    R.id.menu_discover -> podcastFragmentProvider.discover()
                    else -> {
                        supportFragmentManager.fragments.forEach {
                            fragmentTransactor.remove(it).commit()
                        }
                        null
                    }
                }
                fragment?.let { fragmentTransactor.replace(it).commit() }
            }

            if (savedInstanceState == null) {
                setItemSelected(R.id.menu_discover)
            }
        }

        // TODO refactor for player container
        main_menu.applySystemWindowsDesign(applyBottom = true) { v, inset ->
            home_fragment_container.dispatchApplyWindowInsets(
                inset.replaceSystemWindowInsets(
                    inset.systemWindowInsetLeft,
                    inset.systemWindowInsetTop,
                    inset.systemWindowInsetRight,
                    v.measuredHeight
                )
            )
        }
    }
}