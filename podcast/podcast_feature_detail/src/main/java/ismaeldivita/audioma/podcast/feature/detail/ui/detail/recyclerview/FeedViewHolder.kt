package ismaeldivita.audioma.podcast.feature.detail.ui.detail.recyclerview

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ismaeldivita.audioma.podcast.feature.detail.databinding.PodcastFeatureDetailEpisodeItemBinding
import ismaeldivita.audioma.podcast.feature.detail.databinding.PodcastFeatureDetailHeaderBinding

sealed class FeedViewHolder<T : ViewDataBinding>(view: View) : RecyclerView.ViewHolder(view) {

    abstract val binding: T

    class HeaderViewHolder(
        override val binding: PodcastFeatureDetailHeaderBinding,
        imageLoader: RequestManager
    ) : FeedViewHolder<PodcastFeatureDetailHeaderBinding>(binding.root) {

        init {
            binding.imageLoader = imageLoader
        }
    }

    class FeedEpisodeViewHolder(
        override val binding: PodcastFeatureDetailEpisodeItemBinding
    ) : FeedViewHolder<PodcastFeatureDetailEpisodeItemBinding>(binding.root)

}