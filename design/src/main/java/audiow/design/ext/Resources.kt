package audiow.design.ext

import android.content.res.Configuration
import android.content.res.Resources

fun Resources.isNightModeActive(): Boolean =
    when (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> true
        Configuration.UI_MODE_NIGHT_NO -> false
        Configuration.UI_MODE_NIGHT_UNDEFINED -> false
        else -> false
    }