package ismaeldivita.audioma.podcast.feature.detail.ui.detail.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ismaeldivita.audioma.core.monitoring.log.Logger
import ismaeldivita.audioma.core.util.standart.exhaustive
import ismaeldivita.audioma.design.databinding.BindableAdapter
import ismaeldivita.audioma.podcast.feature.detail.R
import ismaeldivita.audioma.podcast.feature.detail.ui.detail.recyclerview.FeedViewHolder.FeedEpisodeViewHolder
import ismaeldivita.audioma.podcast.feature.detail.ui.detail.recyclerview.FeedViewHolder.HeaderViewHolder

internal class FeedAdapter(
    private val imageLoader: RequestManager
) : RecyclerView.Adapter<FeedViewHolder<*>>(), BindableAdapter<List<FeedItem>> {

    private val items: MutableList<FeedItem> = mutableListOf()

    override fun setData(data: List<FeedItem>) {
        val diffCallback = FeedItemDiffCalback(items, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int) = when (items[position]) {
        is FeedItem.Header -> R.layout.podcast_feature_detail_header
        is FeedItem.FeedEpisode -> R.layout.podcast_feature_detail_episode_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        R.layout.podcast_feature_detail_header -> HeaderViewHolder(
            binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            ),
            imageLoader = imageLoader
        )

        R.layout.podcast_feature_detail_episode_item -> FeedEpisodeViewHolder(
            binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        )

        else -> throw IllegalStateException("viewType not supported")
    }

    override fun onViewAttachedToWindow(holder: FeedViewHolder<*>) {
        holder.itemView.requestApplyInsets()
    }

    override fun onBindViewHolder(vh: FeedViewHolder<*>, position: Int) {
        val item = items[position]

        when {
            vh is HeaderViewHolder && item is FeedItem.Header ->
                vh.binding.podcast = item.podcast

            vh is FeedEpisodeViewHolder && item is FeedItem.FeedEpisode ->
                vh.binding.episode = item.episode

            else -> Logger.e("View holder and feed item mismatched")
        }.exhaustive

        vh.binding.executePendingBindings()
    }

}