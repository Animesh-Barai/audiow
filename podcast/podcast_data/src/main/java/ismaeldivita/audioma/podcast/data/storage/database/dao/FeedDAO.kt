package ismaeldivita.audioma.podcast.data.storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ismaeldivita.audioma.podcast.data.storage.database.entity.FeedEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.FeedEpisodeEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.FeedPodcastWrapper

@Dao
internal abstract class FeedDAO {

    @Insert
    protected abstract fun insert(feed: FeedEntity)

    @Insert
    protected abstract fun insert(episodes: List<FeedEpisodeEntity>)

    @Query("SELECT * FROM FEED WHERE id=:podcastId")
    abstract fun getFeedById(podcastId: Long): FeedPodcastWrapper

    @Transaction
    open fun insertWrapper(wrapper: FeedPodcastWrapper) {
        insert(wrapper.feed)
        insert(wrapper.episodes)
    }
}