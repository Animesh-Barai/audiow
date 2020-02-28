package audiow.user.data.storage.firestore.interactor

import audiow.core.interactor.Interactor
import audiow.core.interactor.invoke
import com.google.firebase.firestore.CollectionReference
import io.reactivex.Single
import javax.inject.Inject

internal class GetPodcastCollection @Inject constructor(
    private val userDocument: GetCurrentUserDocument
) : Interactor<Unit, Single<CollectionReference>> {

    override fun invoke(p: Unit): Single<CollectionReference> =
        userDocument().map { it.collection("podcast") }
}