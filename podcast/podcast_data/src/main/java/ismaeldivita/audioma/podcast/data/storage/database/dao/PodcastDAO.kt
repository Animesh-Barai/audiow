package ismaeldivita.audioma.podcast.data.storage.database.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastAndGenreMapEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastArtworkEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastWrapperEntity

@Dao
internal abstract class PodcastDAO {

    @Query("SELECT * FROM PODCAST")
    abstract fun getAll(): Single<List<PodcastWrapperEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insert(podcast: PodcastEntity): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun update(podcast: PodcastEntity)

    @Insert
    protected abstract fun insertArtworkList(artworkList: List<PodcastArtworkEntity>)

    @Query("DELETE FROM PODCAST_ARTWORK WHERE podcastIdFk = :podcastId")
    protected abstract fun deleteAllArtworkList(podcastId: Int)

    @Insert
    protected abstract fun insertPodcastGenre(podcastGenreList: List<PodcastAndGenreMapEntity>)

    @Query("DELETE FROM PODCAST_GENRE WHERE podcastId = :podcastId")
    abstract fun deleteAllGenresRelation(podcastId: Int)

    @Query("SELECT * FROM PODCAST WHERE id=:id")
    abstract fun findById(id: Int): Maybe<PodcastWrapperEntity>

    @Query("SELECT * FROM PODCAST WHERE id IN (:ids)")
    abstract fun findByIds(ids: List<Int>): Single<List<PodcastWrapperEntity>>

    @Query("DELETE FROM PODCAST")
    abstract fun deleteAll(): Completable

    @Delete
    abstract fun delete(model: PodcastEntity): Completable

    @Transaction
    open fun upsert(entity: PodcastEntity) {
        val id = insert(entity)
        if (id == -1L) {
            update(entity)
        }
    }

    @Transaction
    open fun upsertPodcastWrapperTransaction(podcast: PodcastWrapperEntity) {
        upsert(podcast.podcast)

        deleteAllArtworkList(podcast.podcast.id)
        insertArtworkList(podcast.artworkList)

        deleteAllGenresRelation(podcast.podcast.id)
        insertPodcastGenre(podcast.genreIds.map {
            PodcastAndGenreMapEntity(podcast.podcast.id, it)
        })
    }

}