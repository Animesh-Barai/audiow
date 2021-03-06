package audiow.podcast.data.storage.database.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import audiow.podcast.data.storage.database.entity.PodcastAndGenreMapEntity
import audiow.podcast.data.storage.database.entity.PodcastArtworkEntity
import audiow.podcast.data.storage.database.entity.PodcastEntity
import audiow.podcast.data.storage.database.entity.PodcastWrapperEntity

@Dao
internal abstract class PodcastDAO {

    @Query("SELECT * FROM podcast")
    abstract fun getAll(): Single<List<PodcastWrapperEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract fun insert(podcast: PodcastEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun update(podcast: PodcastEntity)

    @Insert
    protected abstract fun insertArtworkList(artworkList: List<PodcastArtworkEntity>)

    @Query("DELETE FROM podcast_artwork WHERE podcastIdFk = :podcastId")
    protected abstract fun deleteAllArtworkList(podcastId: Long)

    @Insert
    protected abstract fun insertPodcastGenre(podcastGenreList: List<PodcastAndGenreMapEntity>)

    @Query("DELETE FROM podcast_genre WHERE podcastId = :podcastId")
    abstract fun deleteAllGenresRelation(podcastId: Long)

    @Query("SELECT * FROM podcast WHERE id=:id")
    abstract fun findById(id: Long): Maybe<PodcastWrapperEntity>

    @Query("SELECT * FROM podcast WHERE id IN (:ids)")
    abstract fun findByIds(ids: List<Long>): Single<List<PodcastWrapperEntity>>

    @Query("DELETE FROM podcast")
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

    @Transaction
    open fun upsertPodcastWrapperTransaction(podcasts: List<PodcastWrapperEntity>) {
        podcasts.forEach(::upsertPodcastWrapperTransaction)
    }
}