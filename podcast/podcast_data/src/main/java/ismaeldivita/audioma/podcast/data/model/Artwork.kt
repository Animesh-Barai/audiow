package ismaeldivita.audioma.podcast.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Artwork(
    val url: String,
    val width: Int,
    val height: Int
) : Parcelable