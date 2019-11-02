package ismaeldivita.audioma.design.widget

import android.content.Context
import android.graphics.drawable.StateListDrawable
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.content.res.getStringOrThrow
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ismaeldivita.audioma.design.R

class ToggleButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : MaterialButton(context, attrs) {

    private val checkedLabel: String
    private val uncheckedLabel: String

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ToggleButton)

        val checkedDrawable = context.getDrawable(
            a.getResourceId(R.styleable.ToggleButton_checkedDrawable, -1)
        )
        val uncheckedDrawable = context.getDrawable(
            a.getResourceId(R.styleable.ToggleButton_uncheckedDrawable, -1)
        )
        val stateDrawable = StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_selected), checkedDrawable)
            addState(intArrayOf(), uncheckedDrawable)
        }
        checkedLabel = a.getStringOrThrow(R.styleable.ToggleButton_checkedLabel)
        uncheckedLabel = a.getStringOrThrow(R.styleable.ToggleButton_uncheckedLabel)

        iconGravity = ICON_GRAVITY_START
        icon = stateDrawable
        isSelected = false

        setOnClickListener {
            if (!isSelected) {
                isSelected = !isSelected
            } else {
                MaterialAlertDialogBuilder(context)
                    .setMessage("Abc asldkaj dasdlkasjd aslk")
                    .setTitle("Title foo")
                    .setPositiveButton("Unsubscribe") { _, _ -> isSelected = false }
                    .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
                    .show()
            }
        }

        a.recycle()
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        val label = if (selected) checkedLabel else uncheckedLabel

        if (text != label) {
            (parent as? ViewGroup)?.let { TransitionManager.beginDelayedTransition(it) }
            text = label
        }
    }

}
