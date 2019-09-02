package ismaeldivita.audioma.app.screens.main.player

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ismaeldivita.audioma.design.coordinator.SmoothScrollBehavior
import ismaeldivita.audioma.design.coordinator.SmoothScrollBehavior.State.*

internal class PlayerBehavior(
    context: Context,
    attrs: AttributeSet?
) : BottomSheetBehavior<View>(context, attrs) {

    val nestedScrollBehavior = SmoothScrollBehavior(context, attrs).apply {
        enterAnimationDuration = 400
        exitAnimationDuration = 400
        listener = {
            Log.i("BOTTOM_SHEET", it.toString())
            if (it == STATE_SCROLLED_DOWN) {
                state = STATE_COLLAPSED
            }
        }
    }
    init {
        isFitToContents = false
    }

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {
        nestedScrollBehavior.onLayoutChild(parent, child, layoutDirection)
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        val nestedBehaviorConsume = nestedScrollBehavior.onStartNestedScroll(
            coordinatorLayout,
            child,
            directTargetChild,
            target,
            axes,
            type
        )
        return if (nestedBehaviorConsume) {
            true
        } else {
            super.onStartNestedScroll(
                coordinatorLayout,
                child,
                directTargetChild,
                target,
                axes,
                type
            )
        }
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) = nestedScrollBehavior.onNestedScroll(
        coordinatorLayout,
        child,
        target,
        dxConsumed,
        dyConsumed,
        dxUnconsumed,
        dyUnconsumed,
        type,
        consumed
    )

    companion object {
        fun from(view: View): PlayerBehavior =
            (view.layoutParams as CoordinatorLayout.LayoutParams)
                .behavior as PlayerBehavior
    }
}