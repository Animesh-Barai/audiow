package audiow.user.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Subscription(
    @DocumentId
     val id: String,
     val feedUrl: String,
     val itunesId: Long
) : Parcelable