package ismaeldivita.audioma.podcast.feature.ui.discover.feed

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ismaeldivita.audioma.design.databinding.BindableAdapter
import ismaeldivita.audioma.design.ext.getPreferredSwatch
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
        )
    )

    override fun getItemCount(): Int = podcasts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.podcast = podcasts[position]
    }

    data class BannerViewData(
        @ColorInt val contrastColor: Int,
        @ColorInt val containerColor: Int
    )

    inner class ViewHolder(
        val binding: PodcastFeatureFeedBannerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val bitmapListener = onResourceReady<Drawable> { drawable ->
            val bitmap = (drawable as? BitmapDrawable)?.bitmap ?: drawable.toBitmap()

            Palette.from(bitmap).generate { palette ->
                palette?.getPreferredSwatch(itemView.context)?.let {
                    binding.viewData = BannerViewData(
                        contrastColor = it.titleTextColor,
                        containerColor = it.rgb
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