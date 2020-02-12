package audiow.design.ext

import android.view.View
import android.view.ViewGroup
import androidx.transition.Transition
import androidx.transition.TransitionManager

internal fun View.beginDelayedTransitionOnParent(transition: Transition? = null) {
    (parent as? ViewGroup)?.let { TransitionManager.beginDelayedTransition(it, transition) }
}