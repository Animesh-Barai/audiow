package ismaeldivita.audioma.design.databinding

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.view.View
import androidx.databinding.BindingAdapter
import ismaeldivita.audioma.design.R
import ismaeldivita.audioma.design.ext.getThemeColor

@BindingAdapter(
    "roundBackgroundColor",
    "roundBackgroundRadius",
    requireAll = false
)
fun roundBackground(
    view: View,
    backgroundColor: Int,
    backgroundRadius: Float
) {
    val drawable = GradientDrawable().apply {
        color = ColorStateList.valueOf(backgroundColor)

        cornerRadius = backgroundRadius.takeIf { it > 0 }
            ?: view.context.resources.getDimension(R.dimen.radius_default)
    }

    view.background = drawable
}

@BindingAdapter(
    "foregroundRippleColor",
    "foregroundRippleRadius",
    requireAll = false
)
fun foregroundRipple(
    view: View,
    rippleColor: Int,
    rippleRadius: Float
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val shape = GradientDrawable().apply {
            if (rippleRadius > 0) cornerRadius = rippleRadius
            setColor(Color.WHITE)
        }
        val finalColor = rippleColor.takeIf { it > 0 }
            ?: view.context.getThemeColor(android.R.attr.colorControlHighlight)

        val ripple = RippleDrawable(ColorStateList.valueOf(finalColor), null, shape)

        view.foreground = ripple
    }
}