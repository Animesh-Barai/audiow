package ismaeldivita.audioma.podcast.feature.ui.discover.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ismaeldivita.audioma.core.monitoring.log.Logger
import ismaeldivita.audioma.core.util.standart.exhaustive
import ismaeldivita.audioma.design.databinding.BindableAdapter
import ismaeldivita.audioma.podcast.R
import ismaeldivita.audioma.podcast.data.model.FeedSection
import ismaeldivita.audioma.podcast.data.model.FeedSection.*
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.feature.ui.discover.feed.FeedViewHolder.*
import java.lang.IllegalStateException

internal class FeedAdapter(
    private val callback: (Action) -> Unit
) : RecyclerView.Adapter<FeedViewHolder>(),
    BindableAdapter<List<FeedSection>> {

    private val feed: MutableList<FeedSection> = mutableListOf()

    override fun setData(data: List<FeedSection>) {
        feed.clear()
        feed.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.podcast_feature_feed_genre -> GenreViewHolder(
                binding = DataBindingUtil.inflate(inflater, viewType, parent, false)
            )

            R.layout.podcast_feature_feed_banner -> BannerViewHolder(
                binding = DataBindingUtil.inflate(inflater, viewType, parent, false)
            )

            R.layout.podcast_feature_feed_highlight -> HighlightViewHolder(
                binding = DataBindingUtil.inflate(inflater, viewType, parent, false)
            )
            else -> throw IllegalStateException("viewType not supported")
        }
    }

    override fun getItemCount(): Int = feed.size

    override fun getItemViewType(position: Int): Int = when (feed[position]) {
        is Banner -> R.layout.podcast_feature_feed_banner
        is Highlight -> R.layout.podcast_feature_feed_highlight
        is GenreSection -> R.layout.podcast_feature_feed_genre
    }

    override fun onBindViewHolder(vh: FeedViewHolder, position: Int) {
        val item = feed[position]

        when {
            vh is BannerViewHolder && item is Banner -> vh.binding.banner = item
            vh is GenreViewHolder && item is GenreSection -> vh.binding.genreSection = item
            vh is HighlightViewHolder && item is Highlight -> vh.binding.highlight = item
            else -> Logger.e("View holder and feed item mismatched")
        }.exhaustive
    }

    sealed class Action {
        class PodcastSelected(podcast: Podcast) : Action()

        class GenreSelected(genre: Genre) : Action()
    }
}