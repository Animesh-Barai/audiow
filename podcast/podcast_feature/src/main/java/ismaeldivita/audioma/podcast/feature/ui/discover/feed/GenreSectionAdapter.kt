package ismaeldivita.audioma.podcast.feature.ui.discover.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ismaeldivita.audioma.design.databinding.BindableAdapter
import ismaeldivita.audioma.podcast.R
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.databinding.PodcastFeatureFeedGenreItemBinding
import ismaeldivita.audioma.podcast.feature.ui.discover.feed.GenreSectionAdapter.ViewHolder

class GenreSectionAdapter(
    private val imageLoader: RequestManager
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
            R.layout.podcast_feature_feed_genre_item,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = podcasts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            podcast = podcasts[position]
            executePendingBindings()
        }
    }

    inner class ViewHolder(
        val binding: PodcastFeatureFeedGenreItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imageLoader = imageLoader
        }
    }
}