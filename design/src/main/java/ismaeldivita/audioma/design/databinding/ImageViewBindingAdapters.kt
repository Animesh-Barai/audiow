package ismaeldivita.audioma.design.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.RequestManager

@BindingAdapter(
    "load",
    "imageLoader",
    "crossFade",
    requireAll = false
)
fun loadGlide(
    imageView: ImageView,
    url: String?,
    requestManager: RequestManager?,
    crossFade: Boolean
) {
    if (requestManager == null) return
    if (url.isNullOrEmpty()) return

    requestManager
        .load(url)
        .into(imageView)
}