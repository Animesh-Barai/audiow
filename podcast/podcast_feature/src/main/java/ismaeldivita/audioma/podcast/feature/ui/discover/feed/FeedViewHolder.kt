package ismaeldivita.audioma.podcast.feature.ui.discover.feed

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ismaeldivita.audioma.design.ext.getPreferredSwatch
import ismaeldivita.audioma.design.ext.onResourceReady
import ismaeldivita.audioma.design.ext.replaceAlpha
import ismaeldivita.audioma.podcast.databinding.PodcastFeatureFeedBannerBinding
import ismaeldivita.audioma.podcast.databinding.PodcastFeatureFeedGenreBinding
import ismaeldivita.audioma.podcast.databinding.PodcastFeatureFeedHighlightBinding

sealed class FeedViewHolder<T : ViewDataBinding>(view: View) : RecyclerView.ViewHolder(view) {

    abstract val imageLoader: RequestManager
    abstract val binding: T

    class BannerViewHolder(
        override val binding: PodcastFeatureFeedBannerBinding,
        override val imageLoader: RequestManager
    ) : FeedViewHolder<PodcastFeatureFeedBannerBinding>(binding.root) {
        init {
            with(binding.podcasts) {
                adapter = BannerAdapter(imageLoader)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    class GenreViewHolder(
        override val binding: PodcastFeatureFeedGenreBinding,
        override val imageLoader: RequestManager
    ) : FeedViewHolder<PodcastFeatureFeedGenreBinding>(binding.root) {

        init {
            with(binding.podcasts) {
                adapter = GenreSectionAdapter(imageLoader).apply { setHasFixedSize(true) }
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    class HighlightViewHolder(
        override val binding: PodcastFeatureFeedHighlightBinding,
        override val imageLoader: RequestManager
    ) : FeedViewHolder<PodcastFeatureFeedHighlightBinding>(binding.root) {

        data class HighlightViewData(
            @ColorInt val contrastColor: Int,
            @ColorInt val containerColor: Int,
            @ColorInt val rippleColor: Int
        )

        private val bitmapListener = onResourceReady<Drawable> { drawable ->
            val bitmap = (drawable as? BitmapDrawable)?.bitmap ?: drawable.toBitmap()

            Palette.from(bitmap).generate { palette ->
                palette?.getPreferredSwatch(itemView.context)?.let {
                    binding.viewData = HighlightViewData(
                        contrastColor = it.titleTextColor,
                        containerColor = it.rgb,
                        rippleColor = it.titleTextColor.replaceAlpha(0.2f)
                    )
                }
            }
        }

        init {
            binding.imageLoader = imageLoader
            binding.listener = bitmapListener
        }
    }
}