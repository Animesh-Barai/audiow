package ismaeldivita.audioma.design.ext

import android.content.Context
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Palette.Swatch

fun Palette.getPreferredSwatch(context: Context): Swatch? {
    val isNightMode = context.resources.isNightModeActive()

    return if (isNightMode) {
        darkMutedSwatch ?: darkVibrantSwatch ?: lightMutedSwatch ?: lightVibrantSwatch
        ?: dominantSwatch
    } else {
        lightMutedSwatch ?: lightVibrantSwatch ?: darkMutedSwatch ?: darkVibrantSwatch
        ?: dominantSwatch
    }
}