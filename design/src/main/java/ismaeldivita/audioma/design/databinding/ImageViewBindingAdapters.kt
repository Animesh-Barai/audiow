package ismaeldivita.audioma.design.databinding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener

@BindingAdapter(
    "load",
    "imageLoader",
    "crossFade",
    "radius",
    "listener",
    requireAll = false
)
fun load(
    imageView: ImageView,
    url: String?,
    requestManager: RequestManager?,
    crossFade: Boolean,
    radius: Double,
    requestListener: RequestListener<Drawable>?
) {
    if (requestManager == null) return
    if (url.isNullOrEmpty()) return

    requestManager.load(url)
        .apply {
            if (radius > 0) transform(RoundedCorners(radius.toInt()))
            if (crossFade) transition(DrawableTransitionOptions.withCrossFade())
            requestListener?.let(::listener)
        }
        .into(imageView)
}