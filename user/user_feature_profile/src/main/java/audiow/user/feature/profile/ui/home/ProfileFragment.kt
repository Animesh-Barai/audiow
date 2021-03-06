package audiow.user.feature.profile.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import audiow.core.android.livedata.observe
import audiow.core.android.ui.fragment.ViewModelFragment
import audiow.core.util.standart.exhaustive
import audiow.user.feature.profile.R
import audiow.user.feature.profile.databinding.UserFeatureProfileBinding


import javax.inject.Inject

internal class ProfileFragment : ViewModelFragment<ProfileViewModel>() {

    @Inject
    lateinit var signOutIntentProvider: SignOutIntentProvider

    private lateinit var binding: UserFeatureProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.user_feature_profile,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()

        observe(viewModel.action) { action ->
            when (action) {
                ProfileAction.OnSignOut -> {
                    val activity = requireActivity()
                    startActivity(signOutIntentProvider.getIntent(activity))
                    activity.finishAffinity()
                }
            }.exhaustive
        }
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}