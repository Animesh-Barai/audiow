package ismaeldivita.audioma.design.databinding

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.databinding.BindingAdapter
import ismaeldivita.audioma.design.R

@BindingAdapter(
    "roundBackgroundColor",
    "roundBackgroundRadius"
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