package audiow.profile.feature.ui.home

import android.content.Context
import android.content.Intent

interface SignOutIntentProvider {

    fun getIntent(context: Context): Intent
}