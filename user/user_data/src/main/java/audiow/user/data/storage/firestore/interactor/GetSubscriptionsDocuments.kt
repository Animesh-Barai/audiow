package audiow.user.data.storage.firestore.interactor

import audiow.core.interactor.Interactor
import audiow.core.interactor.invoke
import audiow.core.monitoring.log.Logger
import audiow.core.util.reactive.scheduler.SchedulersProvider
import audiow.user.data.storage.firestore.document.SubscriptionDocument
import audiow.user.data.storage.firestore.toObservable
import io.reactivex.Observable
import javax.inject.Inject

internal class GetSubscriptionsDocuments @Inject constructor(
    private val userDocument: GetCurrentUserDocument,
    private val schedulersProvider: SchedulersProvider
) : Interactor<Unit, Observable<List<SubscriptionDocument>>> {

    override fun invoke(p: Unit): Observable<List<SubscriptionDocument>> =
        userDocument()
            .flatMapObservable { userDocument ->
                userDocument
                    .collection("podcast")
                    .whereEqualTo("subscribed", true)
                    .toObservable<SubscriptionDocument>()
            }.doOnNext {
                Logger.d(
                    message = "Firestore - New snapshot for subscribed Podcast collection received",
                    properties = mapOf("size" to it.size)
                )
            }
            .observeOn(schedulersProvider.io())
}