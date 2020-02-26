package audiow.user.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Subscription(
     val id: String,
     val feedUrl: String,
     val itunesId: Long
) : Parcelable