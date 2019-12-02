package ismaeldivita.audioma.podcast.feature.discover.ui.discover.recyclerview

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.ViewDataBinding
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ismaeldivita.audioma.design.ext.getPreferredSwatch
import ismaeldivita.audioma.design.ext.getThemeColor
import ismaeldivita.audioma.design.ext.onResourceReady
import ismaeldivita.audioma.design.ext.replaceAlpha
import ismaeldivita.audioma.podcast.feature.discover.R
import ismaeldivita.audioma.podcast.feature.discover.databinding.PodcastFeatureDiscoverBannerBinding
import ismaeldivita.audioma.podcast.feature.discover.databinding.PodcastFeatureDiscoverGenreBinding
import ismaeldivita.audioma.podcast.feature.discover.databinding.PodcastFeatureDiscoverHighlightBinding

internal sealed class DiscoverViewHolder<T : ViewDataBinding>(view: View) :
    RecyclerView.ViewHolder(view) {

    abstract val imageLoader: RequestManager
    abstract val binding: T
    abstract val callback: DiscoverAdapter.FeedCallback

    class BannerViewHolder(
        override val binding: PodcastFeatureDiscoverBannerBinding,
        override val imageLoader: RequestManager,
        override val callback: DiscoverAdapter.FeedCallback,
        private val sharedPool: RecyclerView.RecycledViewPool
    ) : DiscoverViewHolder<PodcastFeatureDiscoverBannerBinding>(binding.root) {
        init {
            with(binding.podcasts) {
                adapter = BannerAdapter(imageLoader, callback)
                setRecycledViewPool(sharedPool)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    .apply {
                        recycleChildrenOnDetach = true
                        isItemPrefetchEnabled = true
                        initialPrefetchItemCount = resources.getInteger(
                            R.integer.podcast_feature_discover_banner_prefetch_count
                        )
                    }
            }
        }
    }

    class GenreViewHolder(
        override val binding: PodcastFeatureDiscoverGenreBinding,
        override val imageLoader: RequestManager,
        override val callback: DiscoverAdapter.FeedCallback,
        private val sharedPool: RecyclerView.RecycledViewPool
    ) : DiscoverViewHolder<PodcastFeatureDiscoverGenreBinding>(binding.root) {

        init {
            with(binding.podcasts) {
                adapter = GenreSectionAdapter(imageLoader, callback)
                setRecycledViewPool(sharedPool)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    .apply {
                        recycleChildrenOnDetach = true
                        isItemPrefetchEnabled = true
                        initialPrefetchItemCount = resources.getInteger(
                            R.integer.podcast_feature_discover_genre_prefetch_count
                        )
                    }
            }
        }
    }

    class HighlightViewHolder(
        override val binding: PodcastFeatureDiscoverHighlightBinding,
        override val imageLoader: RequestManager,
        override val callback: DiscoverAdapter.FeedCallback
    ) : DiscoverViewHolder<PodcastFeatureDiscoverHighlightBinding>(binding.root) {

        data class HighlightViewData(
            @ColorInt val contrastColor: Int,
            @ColorInt val containerColor: Int,
            @ColorInt val rippleColor: Int
        )

        private val bitmapListener = onResourceReady<Drawable> { drawable ->
            val bitmap = (drawable as? BitmapDrawable)?.bitmap ?: drawable.toBitmap()

            Palette.from(bitmap).generate { palette ->
                palette?.getPreferredSwatch(itemView.context)?.let {
                    binding.viewData =
                        HighlightViewData(
                            contrastColor = it.titleTextColor,
                            containerColor = it.rgb,
                            rippleColor = it.titleTextColor.replaceAlpha(0.2f)
                        )
                }
            }
        }

        init {
            val context = itemView.context

            with(binding) {
                imageLoader = this@HighlightViewHolder.imageLoader
                callback = this@HighlightViewHolder.callback
                listener = bitmapListener
                viewData = HighlightViewData(
                    contrastColor = context.getThemeColor(R.attr.colorOnSurface),
                    containerColor = context.getThemeColor(R.attr.colorSurface),
                    rippleColor = context.getThemeColor(R.attr.colorOnSurface)
                )
            }
        }
    }
}