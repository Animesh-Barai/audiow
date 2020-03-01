package audiow.user.data.repository.subscription

import audiow.user.data.model.Subscription
import audiow.user.data.storage.database.entity.SubscriptionEntity
import audiow.user.data.storage.firestore.document.SubscriptionDocument

internal fun Subscription.toEntity(userId: String) = SubscriptionEntity(
    id = id,
    feedUrl = feedUrl,
    itunesId = itunesId,
    userId = userId
)

internal fun SubscriptionEntity.toDomain() = Subscription(
    id = id,
    feedUrl = feedUrl,
    itunesId = itunesId
)

internal fun Subscription.toDocument(subscribed: Boolean = true) = SubscriptionDocument(
    id = id,
    feedUrl = feedUrl,
    itunesId = itunesId,
    subscribed = subscribed
)

internal fun SubscriptionDocument.toEntity(userId: String) = SubscriptionEntity(
    id = id,
    feedUrl = feedUrl,
    itunesId = itunesId,
    userId = userId
)