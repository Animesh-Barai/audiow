package ismaeldivita.audioma.podcast.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
    val id: Int,
    val name: String,
    val topPodcastUrl: String
) : Parcelable