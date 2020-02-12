package audiow.podcast.feature.detail.ui.detail.recyclerview

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import audiow.podcast.feature.detail.databinding.PodcastFeatureDetailEpisodeItemBinding
import audiow.podcast.feature.detail.databinding.PodcastFeatureDetailHeaderBinding

internal sealed class FeedViewHolder<T : ViewDataBinding>(view: View) :
    RecyclerView.ViewHolder(view) {

    abstract val binding: T
    abstract val callback: FeedAdapter.FeedCallback

    class HeaderViewHolder(
        override val binding: PodcastFeatureDetailHeaderBinding,
        override val callback: FeedAdapter.FeedCallback,
        imageLoader: RequestManager
    ) : FeedViewHolder<PodcastFeatureDetailHeaderBinding>(binding.root) {
        init {
            binding.imageLoader = imageLoader
        }
    }

    class FeedEpisodeViewHolder(
        override val binding: PodcastFeatureDetailEpisodeItemBinding,
        override val callback: FeedAdapter.FeedCallback
    ) : FeedViewHolder<PodcastFeatureDetailEpisodeItemBinding>(binding.root) {
        init {
            binding.callback = callback
        }
    }

}