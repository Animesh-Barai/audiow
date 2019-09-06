package ismaeldivita.audioma.podcast.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Podcast(
    val id: Int,
    val title: String,
    val artistName: String,
    val rssUrl: String,
    val artworkList: List<Artwork>,
    val primaryGenre: Genre,
    val genreList: List<Genre>,
    val explicit: Boolean
) : Parcelable