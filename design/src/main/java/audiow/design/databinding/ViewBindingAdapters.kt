package audiow.design.databinding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("isSelected")
fun isSelected(
    view: View,
    isSelected: Boolean
) {
    if (view.isSelected != isSelected) {
        view.isSelected = isSelected
    }
}