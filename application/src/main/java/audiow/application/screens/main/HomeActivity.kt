package audiow.application.screens.main

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import audiow.app.R
import audiow.core.android.ui.fragment.FragmentTransactor
import audiow.core.monitoring.log.Logger
import audiow.design.metrics.applySystemWindowsDesign
import audiow.podcast.feature.discover.PodcastDiscoverFragmentFactory
import audiow.user.data.repository.subscription.SubscriptionWatcher
import audiow.user.feature.profile.ui.ProfileFeatureFragmentFactory
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity() {

    @Inject
    internal lateinit var fragmentTransactor: FragmentTransactor

    @Inject
    internal lateinit var podcastFragmentProvider: PodcastDiscoverFragmentFactory

    @Inject
    internal lateinit var profileFragmentProvider: ProfileFeatureFragmentFactory

    @Inject
    internal lateinit var subWatcher: SubscriptionWatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val a = subWatcher
            .onChanged()
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Logger.d("$it") },
                { Logger.e("$it")})

        with(main_menu) {
            setOnItemSelectedListener { menuId ->
                val fragment = when (menuId) {
                    R.id.menu_discover -> podcastFragmentProvider.discover()
                    R.id.menu_profile -> profileFragmentProvider.home()
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