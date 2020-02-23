package audiow.user.feature.profile.ui.home

import android.content.Context
import android.content.Intent

interface SignOutIntentProvider {

    fun getIntent(context: Context): Intent
}