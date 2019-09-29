package ismaeldivita.audioma.podcast.feature.ui.discover.feed

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ismaeldivita.audioma.podcast.databinding.PodcastFeatureFeedBannerBinding
import ismaeldivita.audioma.podcast.databinding.PodcastFeatureFeedGenreBinding
import ismaeldivita.audioma.podcast.databinding.PodcastFeatureFeedHighlightBinding

sealed class FeedViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    abstract val imageLoader: RequestManager

    class BannerViewHolder(
        val binding: PodcastFeatureFeedBannerBinding,
        override val imageLoader: RequestManager
    ) : FeedViewHolder(binding.root)

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
        }
    }

}