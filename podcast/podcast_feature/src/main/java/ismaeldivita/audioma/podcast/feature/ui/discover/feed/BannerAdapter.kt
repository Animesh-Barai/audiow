package ismaeldivita.audioma.podcast.feature.ui.discover.feed

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color.TRANSPARENT
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.doOnLayout
import androidx.core.view.doOnNextLayout
import androidx.databinding.DataBindingUtil
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ismaeldivita.audioma.design.databinding.BindableAdapter
import ismaeldivita.audioma.design.ext.onResourceReady
import ismaeldivita.audioma.podcast.R
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.databinding.PodcastFeatureFeedBannerItemBinding
import ismaeldivita.audioma.podcast.feature.ui.discover.feed.BannerAdapter.ViewHolder

class BannerAdapter(
    private val imageLoader: RequestManager
) : RecyclerView.Adapter<ViewHolder>(), BindableAdapter<List<Podcast>> {

    private val podcasts: MutableList<Podcast> = mutableListOf()
    override fun setData(data: List<Podcast>) {
        podcasts.clear()
        podcasts.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.podcast_feature_feed_banner_item,
            parent,
            false
        ),
        size = (parent.measuredWidth * if(parent.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){0.35}else{0.70}).toInt()
    )

    override fun getItemCount(): Int = podcasts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.podcast = podcasts[position]
    }

    inner class ViewHolder(
        val binding: PodcastFeatureFeedBannerItemBinding,
        val size: Int
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imageLoader = imageLoader
            binding.size = size

            // TODO refactor - clean up and improve databinding
            binding.listener = onResourceReady<Drawable> { drawable ->
                val bitmap = (drawable as? BitmapDrawable)?.bitmap ?: drawable.toBitmap()
                Palette.from(bitmap).generate { palette ->
                    palette?.dominantSwatch?.let {
                        binding.cardContainer.setCardBackgroundColor(it.rgb)
                        binding.title.setTextColor(it.titleTextColor)
                        binding.artist.setTextColor(it.titleTextColor)

                        val gradient = GradientDrawable(
                            GradientDrawable.Orientation.RIGHT_LEFT,
                            intArrayOf(it.rgb, TRANSPARENT)
                        )

                        // TODO refactor
                        binding.cover.doOnLayout {
                            val rect = Rect().apply { binding.cover.getDrawingRect(this) }
                            rect.left = (rect.width() * 0.85).toInt()
                            gradient.bounds = rect
                            binding.cover.overlay.clear()
                            binding.cover.overlay.add(gradient)
                        }
                    }
                }
            }
        }
    }
}