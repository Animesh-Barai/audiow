package audiow.application.screens.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import audiow.app.R
import audiow.core.android.di.PerActivity
import audiow.core.android.ui.fragment.FragmentTransactor
import javax.inject.Inject

@PerActivity
class HomeFragmentTransactor @Inject constructor(
    private val activity: HomeActivity
) : FragmentTransactor() {

    override val containerId: Int = R.id.home_fragment_container

    override fun getFragmentTransaction(): FragmentTransaction =
        activity
            .supportFragmentManager
            .beginTransaction()

    override fun default(fragment: Fragment, tag: String?) = replace(fragment, tag)
}