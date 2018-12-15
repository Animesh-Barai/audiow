package ismaeldivita.podkast.data.storage.sqlite.dao

import androidx.room.*
import io.reactivex.Single
import ismaeldivita.podkast.data.storage.sqlite.entity.PodcastAndGenreMapEntity
import ismaeldivita.podkast.data.storage.sqlite.entity.PodcastArtworkEntity
import ismaeldivita.podkast.data.storage.sqlite.entity.PodcastEntity
import ismaeldivita.podkast.data.storage.sqlite.entity.PodcastWrapperEntity

@Dao
internal abstract class PodcastDAO {

    @Transaction
    @Query("SELECT * FROM PODCAST")
    abstract fun getAll(): Single<List<PodcastWrapperEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPodcast(podcast: PodcastEntity)

    @Insert
    abstract fun insertArtworkList(artworkList: List<PodcastArtworkEntity>)

    @Insert
    abstract fun insertPodcastGenre(podcastGenreList: List<PodcastAndGenreMapEntity>)

    @Transaction
    open fun podcastWrapperTransaction(podcast: PodcastWrapperEntity) {
        insertPodcast(podcast.podcast)
        insertArtworkList(podcast.artworkList)
        insertPodcastGenre(podcast.genreIds.map { PodcastAndGenreMapEntity(podcast.podcast.id, it) })
    }

}