package audiow.user.data.repository.subscription

import audiow.core.data.repository.RepositoryWatcher
import audiow.core.interactor.invoke
import audiow.core.monitoring.log.Logger
import audiow.user.data.model.Subscription
import audiow.user.data.storage.database.dao.SubscriptionDAO
import audiow.user.data.storage.firestore.interactor.GetSubscriptionsDocuments
import audiow.user.data.storage.firestore.document.SubscriptionDocument
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class SubscriptionWatcher @Inject internal constructor(
    private val getSubscriptionsDocuments: GetSubscriptionsDocuments,
    private val subscriptionDAO: SubscriptionDAO
) : RepositoryWatcher<Subscription> {

    override fun onChanged(): Observable<List<Subscription>> =
        Observable.merge(
            subscriptionDAO.onChanged()
                .map { subsEntity ->
                    subsEntity.map { it.toDomain() }
                }.doOnNext {
                    Logger.d(
                        message = "Room - Subscription Data changed",
                        properties = mapOf("size" to it.size)
                    )
                },

            /**
             * This will open the connection to Firestore and update Room database for
             * every new document, after that we can ignore the document since it will be
             * emitted on the chain above.
             */
            getSubscriptionsDocuments()
                .switchMap { document ->
                    updateDatabaseCache(document).andThen(Observable.never<List<Subscription>>())
                }
        )

    override fun onItemChanged(id: Any): Observable<Subscription> {
        TODO()
    }

    private fun updateDatabaseCache(subs: List<SubscriptionDocument>) = Completable.fromCallable {
        subscriptionDAO.updateSubscriptions(subs.map { it.toEntity() })
    }
}