package ismaeldivita.audioma.podcast.data.repository.genre

import android.content.res.Resources
import android.text.format.DateUtils
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import ismaeldivita.audioma.core.data.preferences.Preferences
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.util.reactive.GlobalCompositeDisposable
import ismaeldivita.audioma.core.util.standart.Tree
import ismaeldivita.audioma.core.util.time.TimeProvider
import ismaeldivita.audioma.podcast.data.R
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.storage.database.dao.GenreDAO
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
    @GlobalCompositeDisposable private val globalDisposable: CompositeDisposable
) : Repository<Genre> {

    override fun onChanged(id: Any): Observable<List<Genre>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemChanged(id: Any): Observable<Genre> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private val LAST_UPDATE_KEY = Preferences.Key.Long("genre_last_update")
        private const val CACHE_TTL = DateUtils.WEEK_IN_MILLIS
    }

    override fun add(element: Genre): Completable = throw UnsupportedOperationException()

    override fun addAll(elements: List<Genre>): Completable = throw UnsupportedOperationException()

    override fun remove(element: Genre): Completable = throw UnsupportedOperationException()

    override fun clear(): Completable = dao.deleteAll()

    override fun findById(id: Any) = dao.findById(id as Long)
        .map { it.toDomain() }

    override fun findByIds(ids: List<Any>): Single<List<Genre>> =
        dao.findByIds(ids.map { it as Long })
            .map { genres -> genres.map { it.toDomain() } }

    override fun getAll(): Single<List<Genre>> =
        dao.getAll()
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
            preferences.write(LAST_UPDATE_KEY, timeProvider.getCurrentTimeMillis())
        }.toSingleDefault(genreListEntity.map { it.toDomain() })
    }

    private fun updateCache() {
        val lastUpdate = preferences.read(LAST_UPDATE_KEY) ?: 0
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
