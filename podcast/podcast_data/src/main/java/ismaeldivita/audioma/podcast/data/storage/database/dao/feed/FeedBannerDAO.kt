package ismaeldivita.audioma.podcast.data.storage.database.dao.feed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import ismaeldivita.audioma.podcast.data.storage.database.entity.feed.FeedBannerEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.feed.FeedBannerPodcastsEntity

@Dao
internal abstract class FeedBannerDAO {

    @Insert
    abstract fun insertBanner(entity: FeedBannerEntity): Long

    @Insert
    abstract fun insertBannerPodcasts(entity: FeedBannerPodcastsEntity)

    @Transaction
    open fun insert(map: Map<Int, List<Long>>) {
        map.forEach { (bannerOrder, podcastIds) ->
            val bannerId = insertBanner(FeedBannerEntity(order = bannerOrder))
            podcastIds.forEach {
                insertBannerPodcasts(FeedBannerPodcastsEntity(bannerId, it))
            }
        }
    }
}