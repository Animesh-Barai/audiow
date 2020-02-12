package audiow.podcast.feature.detail.ui.detail.recyclerview

import androidx.recyclerview.widget.DiffUtil

internal class FeedItemDiffCalback(
    private val oldList: List<FeedItem>,
    private val newList: List<FeedItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]

        return when {
            old is FeedItem.Header && new is FeedItem.Header -> {
                old.podcast.id == old.podcast.id
            }
            old is FeedItem.FeedEpisode && new is FeedItem.FeedEpisode -> {
                old.episode.audioFileUrl == new.episode.audioFileUrl
            }
            else -> false
        }
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val old = oldList[oldPosition]
        val new = newList[newPosition]

        return when {
            old is FeedItem.Header && new is FeedItem.Header -> {
                old.podcast.id == old.podcast.id
            }
            old is FeedItem.FeedEpisode && new is FeedItem.FeedEpisode -> {
                old.episode.title == new.episode.title &&
                        old.episode.coverImageUrl == new.episode.coverImageUrl &&
                        old.episode.duration == new.episode.duration &&
                        old.episode.publicationDate == old.episode.publicationDate
            }
            else -> false
        }
    }
}