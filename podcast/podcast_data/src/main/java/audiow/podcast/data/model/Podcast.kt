package audiow.podcast.data.model

import android.os.Parcelable
import audiow.core.util.standart.sha1
import kotlinx.android.parcel.IgnoredOnParcel
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

    @IgnoredOnParcel
    val artwork = artworkList.maxBy { it.height }!!

}