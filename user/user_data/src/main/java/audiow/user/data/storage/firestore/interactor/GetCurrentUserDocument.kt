package audiow.user.data.storage.firestore.interactor

import audiow.core.interactor.Interactor
import audiow.core.interactor.invoke
import audiow.user.data.interactor.GetCurrentUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single
import javax.inject.Inject

internal class GetCurrentUserDocument @Inject constructor(
    private val currentUser: GetCurrentUser,
    private val firestore: FirebaseFirestore
) : Interactor<Unit, Single<DocumentReference>> {

    override fun invoke(p: Unit): Single<DocumentReference> =
        currentUser().map {
            firestore
                .collection("user")
                .document(it.id)
        }
}