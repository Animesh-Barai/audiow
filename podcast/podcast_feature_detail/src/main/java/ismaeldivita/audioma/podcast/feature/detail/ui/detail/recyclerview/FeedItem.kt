package ismaeldivita.audioma.podcast.feature.detail.ui.detail.recyclerview

import ismaeldivita.audioma.podcast.data.model.Episode
import ismaeldivita.audioma.podcast.data.model.Podcast

sealed class FeedItem {

    class Header(val podcast: Podcast) : FeedItem()

    class FeedEpisode(val episode: Episode) : FeedItem()
}