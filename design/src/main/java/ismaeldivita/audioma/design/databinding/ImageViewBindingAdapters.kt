package ismaeldivita.audioma.design.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter(
    "load",
    "imageLoader",
    "crossFade",
    "radius",
    requireAll = false
)
fun load(
    imageView: ImageView,
    url: String?,
    requestManager: RequestManager?,
    crossFade: Boolean,
    radius: Double
) {
    if (requestManager == null) return
    if (url.isNullOrEmpty()) return

    requestManager.load(url)
        .apply {
            if (radius > 0) transform(RoundedCorners(radius.toInt()))
            if (crossFade) transition(DrawableTransitionOptions.withCrossFade())
        }
        .into(imageView)
}