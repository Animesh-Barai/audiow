package audiow.user.data.repository.subscription

import audiow.core.data.repository.Repository
import audiow.user.data.model.Subscription
import audiow.user.data.storage.dao.SubscriptionDAO
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

internal class SubscriptionRepository @Inject constructor(
    private val subscriptionDAO: SubscriptionDAO
) : Repository<Subscription> {

    override fun add(element: Subscription): Completable = Completable.fromCallable {
        subscriptionDAO.upsert(element.toEntity())
    }

    override fun addAll(elements: List<Subscription>): Completable = Completable.fromCallable {
        subscriptionDAO.upsert(elements.map { it.toEntity() })
    }

    override fun getAll(): Single<List<Subscription>> =
        subscriptionDAO.getAll()
            .map { subscriptions -> subscriptions.map { it.toDomain() } }

    override fun findById(id: Any): Maybe<Subscription> =
        subscriptionDAO.findById(id as Long).map { it.toDomain() }

    override fun findByIds(ids: List<Any>): Single<List<Subscription>> =
        subscriptionDAO.findByIds(ids.map { it as Long })
            .map { subscriptions -> subscriptions.map { it.toDomain() } }

    override fun remove(element: Subscription): Completable =
        subscriptionDAO.delete(element.toEntity())

    override fun clear(): Completable = subscriptionDAO.deleteAll()

}