package audiow.user.data.storage.firestore.document

import com.google.firebase.firestore.DocumentId

data class SubscriptionDocument(
    @DocumentId
    val id: String = "",
    val feedUrl: String = "",
    val itunesId: Long = 0,
    val subscribed: Boolean = false
)