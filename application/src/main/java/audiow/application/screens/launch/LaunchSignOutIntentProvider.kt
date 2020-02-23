package audiow.application.screens.launch

import android.content.Context
import android.content.Intent
import audiow.user.feature.profile.ui.home.SignOutIntentProvider
import javax.inject.Inject

class LaunchSignOutIntentProvider @Inject constructor() : SignOutIntentProvider {

    override fun getIntent(context: Context) = Intent(context, LaunchActivity::class.java)
}