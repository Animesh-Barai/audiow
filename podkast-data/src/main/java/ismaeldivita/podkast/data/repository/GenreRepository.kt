package ismaeldivita.podkast.data.repository

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ismaeldivita.podkast.data.storage.sqlite.dao.GenreDAO
import ismaeldivita.podkast.data.storage.sqlite.entity.GenreWithSubGenre
import ismaeldivita.podkast.service.PodcastService
import ismaeldivita.podkast.service.model.Genre
import ismaeldivita.podkast.service.model.GenreDetail

internal class GenreRepository(
        private val genreDAO: GenreDAO,
        private val service: PodcastService
) : Repository<Genre> {

    override fun add(element: Genre): Completable = throw UnsupportedOperationException()

    override fun remove(element: Genre): Completable = throw UnsupportedOperationException()

    override fun getById(id: Int): Maybe<Genre> = throw UnsupportedOperationException()

    override fun getAll(): Single<List<Genre>> =
    //TODO use countryISO
            genreDAO.getAllWithSubGenres()
                    .flatMap { genreEntities ->
                        if (genreEntities.isEmpty()) {
                            service.getGenreTree()
                                    .map { it.toList() }
                                    .flatMap { updateCache(it) }
                        } else {
                            Single.just(mapEntitiesToGenre(genreEntities))
                        }
                    }

    override fun clear(): Completable = Completable.fromCallable { genreDAO.deleteAll() }

    private fun updateCache(genreList: List<Genre>): Single<List<Genre>> =
            Completable.fromCallable {
                genreDAO.genreTransaction(genreList)
            }.toSingleDefault(genreList)


    private fun mapEntitiesToGenre(entities: List<GenreWithSubGenre>): List<Genre> {

        fun mapSubGenreIdsToGenre(entity: GenreWithSubGenre): List<Genre> = entity.subGenreIds
                .map { subGenreId -> entities.first { it.genre.id == subGenreId } }
                .let(::mapEntitiesToGenre)

        return entities.map {
            Genre(it.genre.id, it.genre.name, GenreDetail(
                    mapSubGenreIdsToGenre(it),
                    it.genre.topPodcastsUrl
            ))
        }
    }

}
