package audiow.user.feature.profile.ui

import androidx.fragment.app.Fragment
import audiow.user.feature.profile.ui.home.ProfileFragment
import javax.inject.Inject

class ProfileFeatureFragmentFactory @Inject constructor() {

    fun home(): Fragment = ProfileFragment.newInstance()
}