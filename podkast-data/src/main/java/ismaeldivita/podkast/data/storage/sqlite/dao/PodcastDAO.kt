package ismaeldivita.podkast.data.storage.sqlite.dao

import androidx.room.*
import io.reactivex.Single
import ismaeldivita.podkast.data.storage.sqlite.entity.PodcastWrapperDB
import ismaeldivita.podkast.data.storage.sqlite.entity.PodcastWrapperDB.PodcastArtworkDB
import ismaeldivita.podkast.data.storage.sqlite.entity.PodcastWrapperDB.PodcastGenreDB

@Dao
internal abstract class PodcastDAO {

    @Transaction
    @Query("SELECT * FROM PODCAST")
    abstract fun getAll(): Single<List<PodcastWrapperDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPodcast(podcast: PodcastWrapperDB.PodcastDB)

    @Insert
    abstract fun insertArtworkList(artworkList: List<PodcastArtworkDB>)

    @Insert
    abstract fun insertPodcastGenre(podcastGenreList: List<PodcastGenreDB>)

    @Transaction
    open fun podcastWrapperTransaction(podcast: PodcastWrapperDB) {
        insertPodcast(podcast.podcast)
        insertArtworkList(podcast.artworkList)
        insertPodcastGenre(podcast.genreIds.map { PodcastGenreDB(podcast.podcast.id, it) })
    }

}