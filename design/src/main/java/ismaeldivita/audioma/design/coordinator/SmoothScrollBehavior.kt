package ismaeldivita.audioma.design.coordinator

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import ismaeldivita.audioma.design.coordinator.SmoothScrollBehavior.State.*

class SmoothScrollBehavior(
    context: Context,
    attrs: AttributeSet?
) : CoordinatorLayout.Behavior<View>(context, attrs) {

    var enterAnimationDuration = 450L
    var exitAnimationDuration = 350L
    var scrollEnabled = true
    var listener: ((State) -> Unit)? = null

    private var height = 0
    private var currentState: State = STATE_SCROLLED_UP
    private var currentAnimator: ViewPropertyAnimator? = null

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {
        val paramsCompat = child.layoutParams as ViewGroup.MarginLayoutParams
        height = child.measuredHeight + paramsCompat.bottomMargin + child.paddingBottom
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean = scrollEnabled && axes == ViewCompat.SCROLL_AXIS_VERTICAL

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
        if (currentState != STATE_SCROLLED_DOWN && dyConsumed > 15) {
            slideDown(child)
        } else if (currentState != STATE_SCROLLED_UP && dyConsumed < -15) {
            slideUp(child)
        }
    }

    fun slideUp(child: View) {
        listener?.invoke(STATE_SCROLLED_UP)
        currentAnimator?.cancel()
        child.clearAnimation()
        currentState = STATE_SCROLLED_UP

        animateChildTo(
            child = child,
            targetY = 0,
            duration = enterAnimationDuration,
            interpolator = LinearOutSlowInInterpolator()
        )
    }

    fun slideDown(child: View) {
        listener?.invoke(STATE_SCROLLED_DOWN)
        currentAnimator?.cancel()
        child.clearAnimation()
        currentState = STATE_SCROLLED_DOWN

        animateChildTo(
            child = child,
            targetY = height,
            duration = exitAnimationDuration,
            interpolator = FastOutLinearInInterpolator()
        )
    }

    private fun animateChildTo(
        child: View,
        targetY: Int,
        duration: Long,
        interpolator: TimeInterpolator
    ) {
        currentAnimator = child
            .animate()
            .translationY(targetY.toFloat())
            .setInterpolator(interpolator)
            .setDuration(duration)
            .setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        currentAnimator = null
                    }
                })
    }

    enum class State {
        STATE_SCROLLED_DOWN,
        STATE_SCROLLED_UP
    }

    companion object {
        fun from(view: View): SmoothScrollBehavior =
            (view.layoutParams as CoordinatorLayout.LayoutParams)
                .behavior as SmoothScrollBehavior
    }
}
