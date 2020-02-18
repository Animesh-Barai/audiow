package audiow.user.signin.ui.signin

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import audiow.core.android.ui.activity.ViewModelActivity
import audiow.user.signin.R
import audiow.user.signin.databinding.UserFeatureSigninActivityBinding

class SignInActivity : ViewModelActivity<SignInViewModel>() {

    private lateinit var binding: UserFeatureSigninActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.user_feature_signin_activity)
    }
}