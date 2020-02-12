package audiow.design.widget

import android.content.Context
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.IntRange
import androidx.core.content.res.getDrawableOrThrow
import androidx.core.content.res.getResourceIdOrThrow
import audiow.design.R
import audiow.design.ext.setProgressAnimate

class ProgressImageButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val iconCompletedDrawable: Drawable by lazy {
        context.getDrawable(iconCompletedRes)
    }
    private val iconView: ImageView
    private val progressView: ProgressBar
    private val iconDrawable: Drawable
    private val iconCompletedRes: Int

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ProgressImageButton)
        iconDrawable = a.getDrawableOrThrow(R.styleable.ProgressImageButton_icon)
        iconCompletedRes = a.getResourceIdOrThrow(R.styleable.ProgressImageButton_iconCompleted)

        iconView = ImageView(context).apply {
            scaleType = ImageView.ScaleType.FIT_XY
            setImageDrawable(iconDrawable)
            layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
        }

        progressView = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal).apply {
            progressDrawable = context.getDrawable(R.drawable.shape_circular_progress)
            isIndeterminate = false
            max = 100
        }

        addView(iconView)
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        progressView.measure(widthMeasureSpec, heightMeasureSpec)
        progressView.layout(
            0,
            0,
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
    }

    override fun setActivated(activated: Boolean) {
        val animationDrawable = iconView.drawable as? AnimatedVectorDrawable

        if (activated) {
            animationDrawable?.registerAnimationCallback(object : Animatable2.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable) {
                    animationDrawable.start()
                }
            })
            animationDrawable?.start()
        } else {
            animationDrawable?.clearAnimationCallbacks()
            animationDrawable?.stop()
            animationDrawable?.reset()
        }
    }

    fun setProgress(@IntRange(from = -1, to = 100) progress: Int) {
        when (progress) {
            -1 -> {
                scaleIcon(1f)
                overlay.remove(progressView)
                iconView.setImageDrawable(iconDrawable)
            }

            in 0..99 -> {
                scaleIcon(0.65f)
                overlay.add(progressView)
                progressView.setProgressAnimate(progress)
                iconView.setImageDrawable(iconDrawable)
            }

            100 -> {
                isActivated = false
                scaleIcon(1f)
                overlay.remove(progressView)
                iconView.setImageDrawable(iconCompletedDrawable)
            }
        }
    }

    private fun scaleIcon(scale: Float) {
        if (iconView.scaleX != scale) {
            iconView.animate()
                .scaleX(scale)
                .scaleY(scale)
                .start()
        }
    }
}
