package ismaeldivita.audioma.design.ext

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange

fun @receiver:ColorInt Int.replaceAlpha(
    @FloatRange(from = 0.0, to = 1.0) alpha: Float
): Int = Color.argb(
    (255 * alpha).toInt(),
    Color.red(this),
    Color.green(this),
    Color.blue(this)
)