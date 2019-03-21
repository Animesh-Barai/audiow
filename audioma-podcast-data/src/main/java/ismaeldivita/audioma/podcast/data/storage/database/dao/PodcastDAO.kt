package ismaeldivita.audioma.podcast.data.storage.database.dao

import androidx.room.*
import io.reactivex.Single
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastAndGenreMapEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastArtworkEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastWrapperEntity

@Dao
internal abstract class PodcastDAO {

    @Transaction
    @Query("SELECT * FROM PODCAST")
    abstract fun getAll(): Single<List<PodcastWrapperEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun insertPodcast(podcast: PodcastEntity)

    @Insert
    protected abstract fun insertArtworkList(artworkList: List<PodcastArtworkEntity>)

    @Insert
    protected abstract fun insertPodcastGenre(podcastGenreList: List<PodcastAndGenreMapEntity>)

    @Transaction
    open fun podcastWrapperTransaction(podcast: PodcastWrapperEntity) {
        insertPodcast(podcast.podcast)
        insertArtworkList(podcast.artworkList)
        insertPodcastGenre(podcast.genreIds.map { PodcastAndGenreMapEntity(podcast.podcast.id, it) })
    }

}