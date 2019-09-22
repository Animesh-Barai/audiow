package ismaeldivita.audioma.design.databinding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.BlurTransformation
import coil.transform.CircleCropTransformation
import coil.transform.GrayscaleTransformation
import coil.transform.RoundedCornersTransformation
import ismaeldivita.audioma.core.util.standart.exhaustive

enum class TransformationType {
    BLUR,
    CIRCLE_CROP,
    GRAYSCALE,
    ROUNDED_CORNERS
}

@BindingAdapter(
    "load",
    "loadPlaceholder",
    "loadCrossFade",
    "loadTransformation",
    "loadTransformationRadius",
    requireAll = false
)
fun load(
    imageView: ImageView,
    url: String?,
    placeholder: Drawable?,
    crossfade: Boolean,
    transformationType: TransformationType?,
    transformationRadius: Float
) {
    if (url.isNullOrEmpty()) return

    val transformation = when (transformationType) {
        TransformationType.BLUR -> BlurTransformation(imageView.context)
        TransformationType.CIRCLE_CROP -> CircleCropTransformation()
        TransformationType.GRAYSCALE -> GrayscaleTransformation()
        TransformationType.ROUNDED_CORNERS -> RoundedCornersTransformation(transformationRadius)
        null -> null
    }.exhaustive

    imageView.load(url) {
        placeholder?.let { placeholder(it) }
        transformation?.let { transformations(it) }
        crossfade(crossfade)
        error(placeholder)
    }
}