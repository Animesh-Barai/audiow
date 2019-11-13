package ismaeldivita.audioma.podcast.data.storage.database.dao.discover

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Single
import ismaeldivita.audioma.podcast.data.storage.database.entity.discover.DiscoverBannerEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.discover.DiscoverBannerPodcastEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.discover.DiscoverBannerWrapperEntity

@Dao
internal abstract class DiscoverBannerDAO {

    @Insert
    protected abstract fun insertBanner(entity: DiscoverBannerEntity): Long

    @Insert
    protected abstract fun insertBannerPodcasts(entity: DiscoverBannerPodcastEntity)

    @Query("SELECT * FROM DISCOVER_BANNER")
    abstract fun getAll(): Single<List<DiscoverBannerWrapperEntity>>

    @Query("DELETE FROM DISCOVER_BANNER")
    abstract fun deleteAll(): Completable

    @Transaction
    open fun insert(map: Map<Int, List<Long>>) {
        map.forEach { (bannerOrder, podcastIds) ->
            val bannerId = insertBanner(DiscoverBannerEntity(order = bannerOrder))
            podcastIds.forEach {
                insertBannerPodcasts(DiscoverBannerPodcastEntity(bannerId, it))
            }
        }
    }
}