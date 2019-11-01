package ismaeldivita.audioma.design.ext

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import android.R.attr.data
import android.R
import android.content.Context
import android.content.res.Resources.Theme
import android.util.TypedValue
import androidx.annotation.AttrRes


fun @receiver:ColorInt Int.replaceAlpha(
    @FloatRange(from = 0.0, to = 1.0) alpha: Float
): Int = Color.argb(
    (255 * alpha).toInt(),
    Color.red(this),
    Color.green(this),
    Color.blue(this)
)

@ColorInt
fun Context.getThemeColor(@AttrRes themeColorRes: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(themeColorRes, typedValue, true)
    return typedValue.data
}