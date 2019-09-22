package ismaeldivita.audioma.podcast.feature.ui.discover.feed

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ismaeldivita.audioma.podcast.databinding.PodcastFeatureFeedBannerBinding
import ismaeldivita.audioma.podcast.databinding.PodcastFeatureFeedGenreBinding
import ismaeldivita.audioma.podcast.databinding.PodcastFeatureFeedHighlightBinding

sealed class FeedViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    class BannerViewHolder(
        val binding: PodcastFeatureFeedBannerBinding
    ) : FeedViewHolder(binding.root)

    class GenreViewHolder(
        val binding: PodcastFeatureFeedGenreBinding
    ) : FeedViewHolder(binding.root) {

        init {
            with(binding.podcasts) {
                adapter = GenreSectionAdapter().apply { setHasFixedSize(true) }
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    class HighlightViewHolder(
        val binding: PodcastFeatureFeedHighlightBinding
    ) : FeedViewHolder(binding.root)

}