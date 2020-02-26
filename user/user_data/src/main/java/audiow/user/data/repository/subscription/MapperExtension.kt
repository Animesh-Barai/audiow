package audiow.user.data.repository.subscription

import audiow.user.data.model.Subscription
import audiow.user.data.storage.entity.SubscriptionEntity

internal fun Subscription.toEntity() = SubscriptionEntity(
    id = id,
    feedUrl = feedUrl,
    itunesId = itunesId
)

internal fun SubscriptionEntity.toDomain() = Subscription(
    id = id,
    feedUrl = feedUrl,
    itunesId = itunesId
)