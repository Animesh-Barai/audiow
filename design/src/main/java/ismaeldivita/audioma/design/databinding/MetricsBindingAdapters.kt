package ismaeldivita.audioma.design.databinding

import android.view.View
import androidx.databinding.BindingAdapter
import ismaeldivita.audioma.design.metrics.applySystemWindowsDesign

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