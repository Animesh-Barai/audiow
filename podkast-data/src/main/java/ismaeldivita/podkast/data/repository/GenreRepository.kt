package ismaeldivita.podkast.data.repository

import android.content.res.Resources
import android.text.format.DateUtils
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import ismaeldivita.podkast.core.util.reactivex.GlobalCompositeDisposable
import ismaeldivita.podkast.core.util.time.TimeProvider
import ismaeldivita.podkast.data.R
import ismaeldivita.podkast.data.storage.database.dao.GenreDAO
import ismaeldivita.podkast.data.storage.database.entity.GenreEntity
import ismaeldivita.podkast.data.storage.database.entity.GenreWithSubGenre
import ismaeldivita.podkast.data.storage.database.entity.SubGenreEntity
import ismaeldivita.podkast.data.storage.preferences.Preferences
import ismaeldivita.podkast.service.PodcastService
import ismaeldivita.podkast.service.model.Genre
import ismaeldivita.podkast.service.model.GenreDetail
import ismaeldivita.podkast.service.model.GenreTree
import javax.inject.Inject

internal class GenreRepository @Inject constructor(
    private val genreDAO: GenreDAO,
    private val service: PodcastService,
    private val resources: Resources,
    private val preferences: Preferences,
    private val timeProvider: TimeProvider,
    @GlobalCompositeDisposable private val globalDisposable: CompositeDisposable
) : Repository<Genre> {

    companion object {
        val LAST_DATABASE_UPDATE_KEY = Preferences.Key("genre_last_update", Long::class)
        const val CACHE_TTL = DateUtils.WEEK_IN_MILLIS
    }

    override fun add(element: Genre): Completable = throw UnsupportedOperationException()

    // TODO handle the case when GENRE was saved on CACHE but then deleted on webservice
    override fun remove(element: Genre): Completable = throw UnsupportedOperationException()

    // TODO bump room to got native maybe support
    override fun getById(id: Int): Maybe<Genre> = getAll().flatMapMaybe { list ->
        list.firstOrNull { it.id == id }?.let { Maybe.just(it) } ?: Maybe.empty()
    }

    override fun clear(): Completable = Completable.fromCallable { genreDAO.deleteAll() }

    override fun getAll(): Single<List<Genre>> =
        genreDAO.getAllWithSubGenres()
            .flatMap { genreEntities ->
                if (genreEntities.isEmpty()) {
                    fetchFromRemote()
                } else {
                    updateCache()
                    Single.just(GenreTree(mapEntitiesToGenre(genreEntities)).toList())
                }
            }


    private fun fetchFromRemote(): Single<List<Genre>> =
        service.getGenreTree(resources.getString(R.string.country_iso))
            .flatMap { updateDatabase(it.toList()) }

    private fun updateDatabase(genreList: List<Genre>): Single<List<Genre>> =
        Completable.fromCallable {
            genreDAO.genreTransaction(
                genreEntityList = genreList.map {
                    GenreEntity(
                        it.id,
                        it.name,
                        it.detail!!.topPodcastsUrl
                    )
                },
                subGenreEntityList = genreList.map { genre ->
                    genre.detail!!.subgenres.map { SubGenreEntity(genre.id, it.id) }
                }.flatten()
            )
            preferences.write(LAST_DATABASE_UPDATE_KEY, timeProvider.getCurrentTimeMillis())
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

    private fun updateCache() {
        val lastUpdate = preferences.read(LAST_DATABASE_UPDATE_KEY) ?: 0
        val cacheDuration = timeProvider.getCurrentTimeMillis() - lastUpdate

        if (cacheDuration >= CACHE_TTL) {
            fetchFromRemote()
                .ignoreElement()
                .onErrorComplete()
                .subscribe()
                .let(globalDisposable::add)
        }
    }

}
