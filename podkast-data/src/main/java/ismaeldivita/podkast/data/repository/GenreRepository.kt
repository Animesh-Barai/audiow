package ismaeldivita.podkast.data.repository

import android.content.res.Resources
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ismaeldivita.podkast.data.R
import ismaeldivita.podkast.data.storage.database.dao.GenreDAO
import ismaeldivita.podkast.data.storage.database.entity.GenreWithSubGenre
import ismaeldivita.podkast.service.PodcastService
import ismaeldivita.podkast.service.model.Genre
import ismaeldivita.podkast.service.model.GenreDetail
import ismaeldivita.podkast.service.model.GenreTree
import javax.inject.Inject

internal class GenreRepository @Inject constructor(
        private val genreDAO: GenreDAO,
        private val service: PodcastService,
        private val resources: Resources
) : Repository<Genre> {

    override fun add(element: Genre): Completable = throw UnsupportedOperationException()

    override fun remove(element: Genre): Completable = throw UnsupportedOperationException()

    override fun getById(id: Int): Maybe<Genre> = throw UnsupportedOperationException()

    override fun getAll(): Single<List<Genre>> =
            genreDAO.getAllWithSubGenres()
                    .flatMap { genreEntities ->
                        if (genreEntities.isEmpty()) {
                            service.getGenreTree(resources.getString(R.string.country_iso))
                                    .map { it.toList() }
                                    .flatMap { updateCache(it) }
                        } else {
                            Single.just(GenreTree(mapEntitiesToGenre(genreEntities)).toList())
                        }
                    }

    override fun clear(): Completable = Completable.fromCallable { genreDAO.deleteAll() }

    private fun updateCache(genreList: List<Genre>): Single<List<Genre>> =
            Completable.fromCallable {
                genreDAO.genreTransaction(genreList)
            }.toSingleDefault(genreList)

    private fun mapEntitiesToGenre(
            entities: List<GenreWithSubGenre>,
            filteredIds: List<Int> = listOf(GenreTree.ROOT_GENRE_ID)
    ): List<Genre> = entities
            .asSequence()
            .filter { filteredIds.contains(it.genre.id) }
            .map {
                Genre(
                        it.genre.id,
                        it.genre.name,
                        GenreDetail(
                                mapEntitiesToGenre(entities, it.subGenreIds),
                                it.genre.topPodcastsUrl
                        )
                )
            }
            .toList()
}
