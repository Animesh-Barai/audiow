package ismaeldivita.audioma.podcast.data.storage.database.dao.feed

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Single
import ismaeldivita.audioma.podcast.data.storage.database.entity.feed.FeedBannerEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.feed.FeedBannerPodcastsEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.feed.FeedBannerWrapperEntity

@Dao
internal abstract class FeedBannerDAO {

    @Insert
    protected abstract fun insertBanner(entity: FeedBannerEntity): Long

    @Insert
    protected abstract fun insertBannerPodcasts(entity: FeedBannerPodcastsEntity)

    @Query("SELECT * FROM FEED_BANNER")
    abstract fun getAll(): Single<List<FeedBannerWrapperEntity>>

    @Query("DELETE FROM FEED_BANNER")
    abstract fun deleteAll(): Completable

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