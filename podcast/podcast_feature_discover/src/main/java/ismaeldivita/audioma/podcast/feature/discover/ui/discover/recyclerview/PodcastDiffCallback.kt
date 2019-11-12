package ismaeldivita.audioma.podcast.feature.discover.ui.discover.recyclerview

import androidx.recyclerview.widget.DiffUtil
import ismaeldivita.audioma.podcast.data.model.Podcast

internal class PodcastDiffCallback(
    private val oldList: List<Podcast>,
    private val newList: List<Podcast>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val old = oldList[oldPosition]
        val new = newList[newPosition]

        return old.artistName == new.artistName &&
                old.title == new.title &&
                old.artwork.url == new.artwork.url
    }
}