package audiow.podcast.feature.detail.model

import audiow.podcast.data.model.Podcast

internal data class PodcastDetail(
    val podcast: Podcast,
    val isSubscribed: Boolean
)