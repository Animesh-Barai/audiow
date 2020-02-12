package audiow.podcast.feature.detail.ui.detail.recyclerview

import audiow.podcast.data.model.Episode
import audiow.podcast.data.model.Podcast

sealed class FeedItem {

    class Header(val podcast: Podcast) : FeedItem()

    class FeedEpisode(val episode: Episode) : FeedItem()
}