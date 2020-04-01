package audiow.podcast.feature.subscriptions.ui.subscriptions.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import audiow.design.databinding.BindableAdapter
import audiow.podcast.data.model.Podcast
import audiow.podcast.feature.subscriptions.R
import audiow.podcast.feature.subscriptions.databinding.PodcastFeatureSubscriptionsItemBinding
import com.bumptech.glide.RequestManager

class SubscriptionAdapter(
    private val imageLoader: RequestManager,
    private val callback: SubscriptionsCallback
) : RecyclerView.Adapter<SubscriptionAdapter.SubscriptionViewHolder>(),
    BindableAdapter<List<Podcast>> {

    private val subscriptions: MutableList<Podcast> = mutableListOf()

    override fun setData(data: List<Podcast>) {
        subscriptions.clear()
        subscriptions.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<PodcastFeatureSubscriptionsItemBinding>(
            inflater,
            R.layout.podcast_feature_subscriptions_item,
            parent,
            false
        )
        binding.imageLoader = imageLoader
        return SubscriptionViewHolder(binding)
    }

    override fun getItemCount(): Int = subscriptions.size

    override fun onBindViewHolder(vh: SubscriptionViewHolder, position: Int) {
        val item = subscriptions[position]
        vh.binding.podcast = item
        vh.binding.executePendingBindings()
    }

    inner class SubscriptionViewHolder(
        val binding: PodcastFeatureSubscriptionsItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    interface SubscriptionsCallback {
        fun onPodcastSelected(podcast: Podcast)
    }
}