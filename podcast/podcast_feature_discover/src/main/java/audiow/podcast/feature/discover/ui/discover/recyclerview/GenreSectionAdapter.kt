package audiow.podcast.feature.discover.ui.discover.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import audiow.design.databinding.BindableAdapter
import audiow.podcast.data.model.Podcast
import audiow.podcast.feature.discover.R
import audiow.podcast.feature.discover.databinding.PodcastFeatureDiscoverGenreItemBinding
import audiow.podcast.feature.discover.ui.discover.recyclerview.GenreSectionAdapter.ViewHolder

internal class GenreSectionAdapter(
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
            R.layout.podcast_feature_discover_genre_item,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = podcasts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            podcast = podcasts[position]
            callback = this@GenreSectionAdapter.callback
            executePendingBindings()
        }
    }

    inner class ViewHolder(
        val binding: PodcastFeatureDiscoverGenreItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imageLoader = imageLoader
        }
    }
}