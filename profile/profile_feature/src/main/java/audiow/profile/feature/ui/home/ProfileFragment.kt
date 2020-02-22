package audiow.profile.feature.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import audiow.core.android.ui.fragment.ViewModelFragment
import audiow.profile.R
import audiow.profile.databinding.ProfileFeatureHomeBinding
import javax.inject.Inject

internal class ProfileFragment : ViewModelFragment<ProfileViewModel>() {

    @Inject
    lateinit var signOutIntentProvider: SignOutIntentProvider

    private lateinit var binding: ProfileFeatureHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.profile_feature_home,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.init()
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}