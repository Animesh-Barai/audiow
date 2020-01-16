package ismaeldivita.audioma.design.ext

import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar

fun ProgressBar.setProgressAnimate(progressTo: Int, duration: Long = 300) {
    val animation = ObjectAnimator.ofInt(this, "progress", this.progress, progressTo)
    animation.duration = duration
    animation.interpolator = DecelerateInterpolator()
    animation.start()
}