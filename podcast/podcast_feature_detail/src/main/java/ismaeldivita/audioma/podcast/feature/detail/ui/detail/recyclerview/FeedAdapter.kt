package ismaeldivita.audioma.podcast.feature.detail.ui.detail.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ismaeldivita.audioma.design.databinding.BindableAdapter
import ismaeldivita.audioma.podcast.data.model.Episode
import ismaeldivita.audioma.podcast.feature.detail.R
import ismaeldivita.audioma.podcast.feature.detail.databinding.PodcastFeatureDetailEpisodeItemBinding

internal class FeedAdapter : RecyclerView.Adapter<FeedAdapter.ViewHolder>(),
    BindableAdapter<List<Episode>> {

    private val episodes: MutableList<Episode> = mutableListOf()

    override fun setData(data: List<Episode>) {
        val diffCallback = EpisodeDiffCalback(episodes, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        episodes.clear()
        episodes.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = episodes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.podcast_feature_detail_episode_item,
            parent,
            false
        )
    )

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        holder.itemView.requestApplyInsets()
    }

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        with(vh.binding) {
            episode = episodes[position]
            executePendingBindings()
        }
    }

    inner class ViewHolder(
        val binding: PodcastFeatureDetailEpisodeItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

}