package audiow.user.data.storage.firestore

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import io.reactivex.Observable

val QuerySnapshot?.isLocal: Boolean
    get() = this != null && metadata.hasPendingWrites()

inline fun <reified T : Any> Query.toObservable(): Observable<List<T>> =
    Observable.create<List<T>> { emitter ->
        val listener = addSnapshotListener { snapshot, e ->
            if (e != null) {
                emitter.onError(e)
            }

            if (snapshot != null && !snapshot.isLocal) {
                try {
                    emitter.onNext(snapshot.map { it.toObject<T>() })
                } catch (error: Throwable) {
                    emitter.onError(error)
                }
            }
        }
        emitter.setCancellable { listener.remove() }
    }
