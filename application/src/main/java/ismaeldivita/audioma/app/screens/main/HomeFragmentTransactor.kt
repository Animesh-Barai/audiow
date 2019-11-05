package ismaeldivita.audioma.app.screens.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ismaeldivita.audioma.app.R
import ismaeldivita.audioma.core.android.di.PerActivity
import ismaeldivita.audioma.core.android.ui.FragmentTransactor
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