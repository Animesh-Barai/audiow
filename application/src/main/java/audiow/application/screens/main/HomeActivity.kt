package audiow.application.screens.main

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import audiow.app.R
import audiow.core.android.ui.fragment.FragmentTransactor
import audiow.design.metrics.applySystemWindowsDesign
import audiow.podcast.feature.discover.PodcastDiscoverFragmentFactory
import audiow.podcast.feature.subscriptions.PodcastSubscriptionsFragmentFactory
import audiow.user.feature.profile.ui.ProfileFeatureFragmentFactory
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity() {

    @Inject
    internal lateinit var fragmentTransactor: FragmentTransactor

    @Inject
    internal lateinit var discoverProvider: PodcastDiscoverFragmentFactory
    @Inject
    internal lateinit var profileProvider: ProfileFeatureFragmentFactory
    @Inject
    internal lateinit var subscriptionProvider: PodcastSubscriptionsFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        with(main_menu) {
            setOnItemSelectedListener { menuId ->
                val fragment = when (menuId) {
                    R.id.menu_podcasts -> subscriptionProvider.subscriptions()
                    R.id.menu_discover -> discoverProvider.discover()
                    R.id.menu_profile -> profileProvider.home()
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