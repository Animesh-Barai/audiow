package audiow.profile.feature.ui

import androidx.fragment.app.Fragment
import audiow.profile.feature.ui.home.ProfileFragment
import javax.inject.Inject

class ProfileFeatureFragmentFactory @Inject constructor() {

    fun home(): Fragment = ProfileFragment.newInstance()
}