package ismaeldivita.audioma.podcast.data.repository

import android.content.res.Resources
import android.text.format.DateUtils
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import ismaeldivita.audioma.core.data.preferences.Preferences
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.util.reactive.GlobalCompositeDisposable
import ismaeldivita.audioma.core.util.reactive.SchedulersProvider
import ismaeldivita.audioma.core.util.standart.Tree
import ismaeldivita.audioma.core.util.time.TimeProvider
import ismaeldivita.audioma.podcast.data.R
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.storage.database.dao.GenreDAO
import ismaeldivita.audioma.podcast.data.storage.database.entity.GenreEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.SubGenreEntity
import ismaeldivita.audioma.podcast.service.itunes.ItunesService
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesGenre
import javax.inject.Inject

internal class GenreRepository @Inject constructor(
    private val dao: GenreDAO,
    private val service: ItunesService,
    private val resources: Resources,
    private val preferences: Preferences,
    private val timeProvider: TimeProvider,
    private val schedulers: SchedulersProvider,
    @GlobalCompositeDisposable private val globalDisposable: CompositeDisposable
) : Repository<Genre> {

    companion object {
        val LAST_DATABASE_UPDATE_KEY = Preferences.Key.Long("genre_last_update")
        const val CACHE_TTL = DateUtils.WEEK_IN_MILLIS
    }

    override fun add(element: Genre): Completable = throw UnsupportedOperationException()

    override fun remove(element: Genre): Completable = throw UnsupportedOperationException()

    override fun clear(): Completable = dao.deleteAll().subscribeOn(schedulers.io())

    override fun findById(id: Any) = dao.findById(id as Int)
        .map { it.toDomain() }
        .subscribeOn(schedulers.io())

    override fun getAll(): Single<List<Genre>> =
        dao.getAll()
            .subscribeOn(schedulers.io())
            .flatMap { genreEntities ->
                if (genreEntities.isEmpty()) {
                    fetchFromRemote()
                } else {
                    updateCache()
                    Single.just(genreEntities.map { it.toDomain() })
                }
            }

    private fun fetchFromRemote(): Single<List<Genre>> =
        service.getGenreTree(resources.getString(R.string.country_iso))
            .flatMap { updateDatabase(it.toList()) }

    private fun updateDatabase(genreList: List<Tree.Node<ItunesGenre>>): Single<List<Genre>> {
        val genreListEntity = genreList.map { it.value.toEntity() }

        val subGenreEntityList = genreList.map { genre ->
            genre.children.map {
                SubGenreEntity(
                    genreId = genre.value.id,
                    subGenreId = it.value.id
                )
            }
        }.flatten()

        return Completable.fromCallable {
            dao.genreTransaction(
                genreEntityList = genreListEntity,
                subGenreEntityList = subGenreEntityList
            )
            preferences.write(LAST_DATABASE_UPDATE_KEY, timeProvider.getCurrentTimeMillis())
        }.toSingleDefault(genreListEntity.map { it.toDomain() })
    }

    private fun updateCache() {
        val lastUpdate = preferences.read(LAST_DATABASE_UPDATE_KEY) ?: 0
        val cacheDuration = timeProvider.getCurrentTimeMillis() - lastUpdate

        if (cacheDuration >= CACHE_TTL) {
            fetchFromRemote()
                .ignoreElement()
                .onErrorComplete()
                .subscribeOn(schedulers.io())
                .subscribe()
                .let(globalDisposable::add)
        }
    }

    private fun ItunesGenre.toEntity() = GenreEntity(
        id = id,
        name = name,
        topPodcastsUrl = topPodcastsUrl
    )

    private fun GenreEntity.toDomain() = Genre(
        id = id,
        name = name,
        topPodcastUrl = topPodcastsUrl
    )

}