package ismaeldivita.audioma.podcast.feature.detail.ui.detail.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ismaeldivita.audioma.core.monitoring.log.Logger
import ismaeldivita.audioma.core.util.standart.exhaustive
import ismaeldivita.audioma.design.databinding.BindableAdapter
import ismaeldivita.audioma.podcast.data.model.Episode
import ismaeldivita.audioma.podcast.feature.detail.R
import ismaeldivita.audioma.podcast.feature.detail.ui.detail.recyclerview.FeedItem.*
import ismaeldivita.audioma.podcast.feature.detail.ui.detail.recyclerview.FeedViewHolder.FeedEpisodeViewHolder
import ismaeldivita.audioma.podcast.feature.detail.ui.detail.recyclerview.FeedViewHolder.HeaderViewHolder

internal class FeedAdapter(
    private val imageLoader: RequestManager,
    private val callback: FeedCallback
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
        is Header -> R.layout.podcast_feature_detail_header
        is FeedEpisode -> R.layout.podcast_feature_detail_episode_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.podcast_feature_detail_header -> HeaderViewHolder(
                binding = DataBindingUtil.inflate(inflater, viewType, parent, false),
                callback = callback,
                imageLoader = imageLoader
            )

            R.layout.podcast_feature_detail_episode_item -> FeedEpisodeViewHolder(
                binding = DataBindingUtil.inflate(inflater, viewType, parent, false),
                callback = callback
            )
            else -> throw IllegalStateException("viewType not supported")
        }
    }

    override fun onViewAttachedToWindow(holder: FeedViewHolder<*>) {
        holder.itemView.requestApplyInsets()
    }

    override fun onBindViewHolder(vh: FeedViewHolder<*>, position: Int) {
        val item = items[position]

        when {
            vh is HeaderViewHolder && item is Header -> vh.binding.podcast = item.podcast
            vh is FeedEpisodeViewHolder && item is FeedEpisode -> vh.binding.episode = item.episode
            else -> Logger.e("View holder and feed item mismatched")
        }.exhaustive

        vh.binding.executePendingBindings()
    }

    interface FeedCallback {
        fun onEpisodeSelected(episode: Episode, view: View)
    }
}