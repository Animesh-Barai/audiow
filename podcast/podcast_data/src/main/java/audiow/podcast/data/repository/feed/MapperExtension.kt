package audiow.podcast.data.repository.feed

import audiow.core.util.time.RFC822DateParser
import audiow.podcast.data.model.Episode
import audiow.podcast.data.model.Feed
import audiow.podcast.data.storage.database.entity.FeedEntity
import audiow.podcast.data.storage.database.entity.FeedEpisodeEntity
import audiow.podcast.data.storage.database.entity.FeedPodcastWrapper
import audiow.podcast.service.itunes.model.ItunesPodcastEpisode
import audiow.podcast.service.itunes.model.ItunesPodcastFeed

internal fun ItunesPodcastFeed.toEntity(
    podcastId: Long,
    lastModified: String? = null,
    eTag: String? = null
) = FeedEntity(
    id = podcastId,
    description = description,
    language = languageIso639,
    metadata = FeedEntity.Metadata(lastModified, eTag)
)

internal fun ItunesPodcastEpisode.toEpisodeEntity(feedId: Long) = FeedEpisodeEntity(
    audioFileUrl = audioFileUrl,
    feedId = feedId,
    title = title,
    subtitle = subtitle,
    description = description,
    duration = duration,
    isExplicit = isExplicit,
    publicationDateRFC822 = publicationDateRFC822,
    coverImageUrl = coverImageUrl
)

internal fun Feed.toEntity() = FeedEntity(
    id = podcastId,
    description = description,
    language = language
)

internal fun Episode.toEntity(feedId: Long) = FeedEpisodeEntity(
    audioFileUrl = audioFileUrl,
    feedId = feedId,
    title = title,
    subtitle = subtitle,
    description = description,
    duration = duration,
    isExplicit = isExplicit,
    coverImageUrl = coverImageUrl,
    publicationDateRFC822 = publicationDateRFC822
)

internal fun FeedEpisodeEntity.toDomain(
    dateParser: RFC822DateParser
) = Episode(
    id = audioFileUrl,
    title = title,
    subtitle = subtitle,
    description = description,
    audioFileUrl = audioFileUrl,
    duration = duration,
    isExplicit = isExplicit,
    coverImageUrl = coverImageUrl,
    publicationDate = dateParser.parse(publicationDateRFC822),
    publicationDateRFC822 = publicationDateRFC822
)

internal fun FeedPodcastWrapper.toDomain(
    dateParser: RFC822DateParser
) = Feed(
    podcastId = feed.id,
    description = feed.description,
    language = feed.language,
    episodes = episodes.map { it.toDomain(dateParser) }
)