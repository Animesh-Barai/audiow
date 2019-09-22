package ismaeldivita.audioma.podcast.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Podcast(
    val id: Long,
    val title: String,
    val artistName: String,
    val rssUrl: String,
    val artworkList: List<Artwork>,
    val primaryGenre: Genre,
    val genreList: List<Genre>,
    val explicit: Boolean
) : Parcelable {

    fun getArtwork(minWidthInPx: Int = 0) = artworkList.firstOrNull {
        it.width > minWidthInPx
    } ?: artworkList.last()

}