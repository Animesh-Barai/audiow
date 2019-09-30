package ismaeldivita.audioma.design.ext

import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

inline fun <T> onResourceReady(crossinline block: (T) -> Unit) = object : RequestListener<T> {

    override fun onResourceReady(
        resource: T,
        model: Any?,
        target: Target<T>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        block(resource)
        return false
    }

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<T>?,
        isFirstResource: Boolean
    ): Boolean {
        return false
    }

}