package audiow.design.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.content.res.getStringOrThrow
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import audiow.design.R

/**
 *  A toggle button based on [MaterialButton] which apply two different styles based on
 *  [android.R.attr.state_selected].
 *
 *  A confirmation dialog will popup on deselected events if
 *  [R.styleable.ToggleButton_confirmationMessage] and
 *  [R.styleable.ToggleButton_confirmationActionLabel] are setup.
 *
 *  We're using as workaround [android.R.attr.state_selected] state since [MaterialButton] is not
 *  updating the drawable state when the [android.R.attr.state_checked]
 *
 *  @see [onToggleListener]
 */
class ToggleButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : MaterialButton(context, attrs) {

    private val checkedLabel: String
    private val uncheckedLabel: String
    private val uncheckedDrawable: Drawable?
    private val checkedDrawable: Drawable?

    var onToggleListener: ToggleListener? = null

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ToggleButton)

        checkedDrawable = context.getDrawable(
            a.getResourceId(R.styleable.ToggleButton_checkedDrawable, -1)
        )
        uncheckedDrawable = context.getDrawable(
            a.getResourceId(R.styleable.ToggleButton_uncheckedDrawable, -1)
        )
        val confirmationTitle = a.getString(R.styleable.ToggleButton_confirmationTitle)
            ?: resources.getString(R.string.confirmation)

        val confirmationMessage = a.getString(R.styleable.ToggleButton_confirmationMessage)
        val confirmationActionLabel = a.getString(R.styleable.ToggleButton_confirmationActionLabel)

        checkedLabel = a.getStringOrThrow(R.styleable.ToggleButton_checkedLabel)
        uncheckedLabel = a.getStringOrThrow(R.styleable.ToggleButton_uncheckedLabel)

        iconGravity = ICON_GRAVITY_START
        icon = uncheckedDrawable
        text = uncheckedLabel

        setOnClickListener {
            /**
             * If confirmationMessage and confirmationActionLabel is setup shows the confirmation
             * dialog on unselected events
             */
            if (!isSelected || (confirmationMessage == null && confirmationActionLabel == null)) {
                isSelected = !isSelected
            } else {
                MaterialAlertDialogBuilder(context)
                    .setTitle(confirmationTitle)
                    .setMessage(confirmationMessage)
                    .setPositiveButton(confirmationActionLabel) { _, _ -> isSelected = false }
                    .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.cancel() }
                    .show()
            }
        }

        a.recycle()
    }


    override fun setSelected(selected: Boolean) {
        val wasSelected = isSelected
        super.setSelected(selected)

        if (wasSelected == selected) return

        onToggleListener?.onToggleChanged(selected)

        icon = if (selected) checkedDrawable else uncheckedDrawable

        val label = if (selected) checkedLabel else uncheckedLabel
        if (text != label) {
            (parent as? ViewGroup)?.let { TransitionManager.beginDelayedTransition(it) }
            text = label
        }
    }

    interface ToggleListener {
        fun onToggleChanged(isSelectable: Boolean)
    }
}
