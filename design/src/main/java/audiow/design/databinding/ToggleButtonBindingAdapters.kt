package audiow.design.databinding

import androidx.databinding.BindingAdapter
import audiow.design.widget.ToggleButton

@BindingAdapter("onToggleListener")
fun onToggleListener(
    view: ToggleButton,
    listener: ToggleButton.ToggleListener
) {
    view.onToggleListener = listener
}