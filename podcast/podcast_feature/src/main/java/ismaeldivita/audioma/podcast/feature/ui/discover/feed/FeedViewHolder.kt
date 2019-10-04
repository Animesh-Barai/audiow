package ismaeldivita.audioma.podcast.feature.ui.discover.feed

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ismaeldivita.audioma.design.ext.onResourceReady
import ismaeldivita.audioma.podcast.databinding.PodcastFeatureFeedBannerBinding
import ismaeldivita.audioma.podcast.databinding.PodcastFeatureFeedGenreBinding
import ismaeldivita.audioma.podcast.databinding.PodcastFeatureFeedHighlightBinding

sealed class FeedViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    abstract val imageLoader: RequestManager

    class BannerViewHolder(
        val binding: PodcastFeatureFeedBannerBinding,
        override val imageLoader: RequestManager
    ) : FeedViewHolder(binding.root) {
        init {
            with(binding.podcasts) {
                adapter = BannerAdapter(imageLoader).apply { setHasFixedSize(true) }
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    class GenreViewHolder(
        val binding: PodcastFeatureFeedGenreBinding,
        override val imageLoader: RequestManager
    ) : FeedViewHolder(binding.root) {

        init {
            with(binding.podcasts) {
                adapter = GenreSectionAdapter(imageLoader).apply { setHasFixedSize(true) }
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    class HighlightViewHolder(
        val binding: PodcastFeatureFeedHighlightBinding,
        override val imageLoader: RequestManager
    ) : FeedViewHolder(binding.root) {

        init {
            binding.imageLoader = imageLoader
            // TODO refactor - clean up and improve databinding
            binding.listener = onResourceReady<Drawable> { drawable ->
                val bitmap = (drawable as? BitmapDrawable)?.bitmap ?: drawable.toBitmap()
                Palette.from(bitmap).generate { palette ->
                    palette?.dominantSwatch?.let {
                        binding.title.setTextColor(it.titleTextColor)
                        binding.artist.setTextColor(it.titleTextColor)
                        binding.subscribe.setTextColor(it.titleTextColor)
                        binding.subscribe.strokeColor = ColorStateList.valueOf(it.titleTextColor)

                        // TODO extract to an extension
                        val ripple = Color.argb(
                            (Color.alpha(it.titleTextColor) * 0.2).toInt(),
                            Color.red(it.titleTextColor),
                            Color.green(it.titleTextColor),
                            Color.blue(it.titleTextColor)
                        )

                        binding.subscribe.rippleColor = ColorStateList.valueOf(ripple)
                        binding.cardContainer.setCardBackgroundColor(it.rgb)

                        val gradient = GradientDrawable(
                            GradientDrawable.Orientation.LEFT_RIGHT,
                            intArrayOf(it.rgb, Color.TRANSPARENT)
                        )
                        val rect = Rect().apply { binding.cover.getDrawingRect(this) }

                        rect.right = (rect.width() * 0.15).toInt()
                        gradient.bounds = rect
                        binding.cover.overlay.clear()
                        binding.cover.overlay.add(gradient)
                    }
                }
            }
        }

    }
}