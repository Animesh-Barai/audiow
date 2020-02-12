package audiow.podcast.feature.discover.ui.discover.recyclerview

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import audiow.design.databinding.BindableAdapter
import audiow.design.ext.getPreferredSwatch
import audiow.design.ext.getThemeColor
import audiow.design.ext.onResourceReady
import audiow.podcast.data.model.Podcast
import audiow.podcast.feature.discover.R
import audiow.podcast.feature.discover.databinding.PodcastFeatureDiscoverBannerItemBinding
import audiow.podcast.feature.discover.ui.discover.recyclerview.BannerAdapter.ViewHolder

internal class BannerAdapter(
    private val imageLoader: RequestManager,
    private val callback: DiscoverAdapter.DiscoverCallback
) : RecyclerView.Adapter<ViewHolder>(), BindableAdapter<List<Podcast>> {

    private val podcasts: MutableList<Podcast> = mutableListOf()

    override fun setData(data: List<Podcast>) {
        val diffCallback = PodcastDiffCallback(podcasts, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        podcasts.clear()
        podcasts.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.podcast_feature_discover_banner_item,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = podcasts.size

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        with(vh.binding) {
            podcast = podcasts[position]
            callback = this@BannerAdapter.callback
            executePendingBindings()
        }
    }

    data class BannerViewData(
        @ColorInt val contrastColor: Int,
        @ColorInt val containerColor: Int
    )

    inner class ViewHolder(
        val binding: PodcastFeatureDiscoverBannerItemBinding
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
            val context = itemView.context
            with(binding) {
                imageLoader = this@BannerAdapter.imageLoader
                listener = bitmapListener
                viewData = BannerViewData(
                    contrastColor = context.getThemeColor(R.attr.colorOnSurface),
                    containerColor = context.getThemeColor(R.attr.colorSurface)
                )
            }
        }
    }
}