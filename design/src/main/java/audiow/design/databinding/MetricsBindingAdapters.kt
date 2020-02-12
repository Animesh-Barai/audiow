package audiow.design.databinding

import android.view.View
import androidx.databinding.BindingAdapter
import audiow.design.metrics.applySystemWindowsDesign

@BindingAdapter(
    "paddingLeftSystemWindowInsets",
    "paddingTopSystemWindowInsets",
    "paddingRightSystemWindowInsets",
    "paddingBottomSystemWindowInsets",
    requireAll = false
)
fun applySystemWindowsDesign(
    view: View,
    applyLeft: Boolean,
    applyTop: Boolean,
    applyRight: Boolean,
    applyBottom: Boolean
) {
    view.applySystemWindowsDesign(applyLeft, applyTop, applyRight, applyBottom)
}

@BindingAdapter("android:layout_height")
fun setLayoutHeight(view: View, height: Int) {
    val layoutParams = view.layoutParams
    layoutParams.height = height
    view.layoutParams = layoutParams
}

@BindingAdapter("android:layout_width")
fun setLayoutWidth(view: View, width: Int) {
    val layoutParams = view.layoutParams
    layoutParams.width = width
    view.layoutParams = layoutParams
}