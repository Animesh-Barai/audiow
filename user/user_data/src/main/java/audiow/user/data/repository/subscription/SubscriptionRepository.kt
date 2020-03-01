package audiow.user.data.repository.subscription

import audiow.core.data.repository.Repository
import audiow.core.interactor.invoke
import audiow.user.data.interactor.GetCurrentUser
import audiow.user.data.model.Subscription
import audiow.user.data.storage.database.dao.SubscriptionDAO
import audiow.user.data.storage.firestore.interactor.GetPodcastCollection
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

internal class SubscriptionRepository @Inject constructor(
    private val subscriptionDAO: SubscriptionDAO,
    private val podcastCollection: GetPodcastCollection,
    private val getCurrentUser: GetCurrentUser
) : Repository<Subscription> {

    override fun add(element: Subscription): Completable =
        getCurrentUser()
            .flatMapCompletable { user ->
                Completable.fromCallable { subscriptionDAO.upsert(element.toEntity(user.id)) }
            }
            .andThen(
                podcastCollection()
                    .doOnSuccess { it.document(element.id).set(element.toDocument()) }
                    .ignoreElement()
            )

    override fun addAll(elements: List<Subscription>): Completable =
        getCurrentUser()
            .flatMapCompletable { user ->
                Completable.fromCallable {
                    subscriptionDAO.upsert(elements.map { it.toEntity(user.id) })
                }
            }.andThen(
                podcastCollection()
                    .doOnSuccess { collection ->
                        elements.forEach { collection.document(it.id).set(it.toDocument()) }
                    }.ignoreElement()
            )

    override fun getAll(): Single<List<Subscription>> =
        subscriptionDAO.getAll()
            .map { subscriptions -> subscriptions.map { it.toDomain() } }

    override fun findById(id: Any): Maybe<Subscription> =
        subscriptionDAO.findById(id as String).map { it.toDomain() }

    override fun findByIds(ids: List<Any>): Single<List<Subscription>> =
        subscriptionDAO.findByIds(ids.map { it as String })
            .map { subscriptions -> subscriptions.map { it.toDomain() } }

    override fun remove(element: Subscription): Completable =
        getCurrentUser()
            .flatMapCompletable { user ->
                subscriptionDAO.delete(element.toEntity(user.id))
            }
            .andThen(
                podcastCollection()
                    .doOnSuccess {
                        it.document(element.id).set(element.toDocument(subscribed = false))
                    }
                    .ignoreElement()
            )

    override fun clear(): Completable = subscriptionDAO.deleteAll()

}