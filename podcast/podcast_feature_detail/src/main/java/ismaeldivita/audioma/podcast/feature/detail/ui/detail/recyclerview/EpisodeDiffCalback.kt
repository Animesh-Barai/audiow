package ismaeldivita.audioma.podcast.feature.detail.ui.detail.recyclerview

import androidx.recyclerview.widget.DiffUtil
import ismaeldivita.audioma.podcast.data.model.Episode

internal class EpisodeDiffCalback(
    private val oldList: List<Episode>,
    private val newList: List<Episode>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].audioFileUrl == newList[newItemPosition].audioFileUrl
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val old = oldList[oldPosition]
        val new = newList[newPosition]

        return old.title == new.title &&
                old.coverImageUrl == new.coverImageUrl &&
                old.duration == new.duration &&
                old.publicationDate == old.publicationDate
    }

}