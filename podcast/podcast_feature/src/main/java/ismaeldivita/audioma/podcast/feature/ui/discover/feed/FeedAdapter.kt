package ismaeldivita.audioma.podcast.feature.ui.discover.feed

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import ismaeldivita.audioma.core.util.standart.exhaustive
import ismaeldivita.audioma.design.databinding.BindableAdapter
import ismaeldivita.audioma.podcast.data.model.FeedSection
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.feature.ui.discover.feed.FeedAdapter.ViewHolder

class FeedAdapter(
    private val callback: (Action) -> Unit
) : RecyclerView.Adapter<ViewHolder>(),
    BindableAdapter<List<FeedSection>> {

    private val feed: MutableList<FeedSection> = mutableListOf()

    override fun setData(data: List<FeedSection>) {
        feed.clear()
        feed.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = TextView(parent.context).apply {
            setPadding(12)
        }

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = feed.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = when (val item = feed[position]) {
            is FeedSection.Banner -> item.podcasts.joinToString { pod -> pod.title }
            is FeedSection.Highlight -> item.podcast.title
            is FeedSection.GenreSection -> "${item.genre.name} - ${item.podcasts.size} podcasts"
        }.exhaustive
    }

    inner class ViewHolder(val title: TextView) : RecyclerView.ViewHolder(title)

    sealed class Action {
        class PodcastSelected(podcast: Podcast) : Action()

        class GenreSelected(genre: Genre) : Action()
    }
}