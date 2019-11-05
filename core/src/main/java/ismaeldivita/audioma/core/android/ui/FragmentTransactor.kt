package ismaeldivita.audioma.core.android.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

/**
 *  Protocol to provide a generic way to fragments perform inner transactions using the activity
 *  fragment manager without knowing all details for the [FragmentTransaction] like containerId,
 *  default animations, etc...
 */
abstract class FragmentTransactor {

    protected abstract val containerId: Int

    protected abstract fun getFragmentTransaction(): FragmentTransaction

    abstract fun default(fragment: Fragment, tag: String? = null): FragmentTransaction

    fun add(fragment: Fragment, tag: String? = null): FragmentTransaction =
        getFragmentTransaction().add(containerId, fragment, tag)

    fun replace(fragment: Fragment, tag: String? = null): FragmentTransaction =
        getFragmentTransaction().replace(containerId, fragment, tag)

    fun remove(fragment: Fragment): FragmentTransaction =
        getFragmentTransaction().remove(fragment)
}