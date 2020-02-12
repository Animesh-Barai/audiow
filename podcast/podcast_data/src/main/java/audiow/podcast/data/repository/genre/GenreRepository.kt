package audiow.podcast.data.repository.genre

import android.content.res.Resources
import android.text.format.DateUtils
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import audiow.core.data.preferences.Preferences
import audiow.core.data.repository.Repository
import audiow.core.util.reactive.GlobalCompositeDisposable
import audiow.core.util.standart.Tree
import audiow.core.util.time.TimeProvider
import audiow.podcast.data.R
import audiow.podcast.data.model.Genre
import audiow.podcast.data.storage.database.dao.GenreDAO
import audiow.podcast.data.storage.database.entity.SubGenreEntity
import audiow.podcast.service.itunes.ItunesService
import audiow.podcast.service.itunes.model.ItunesGenre
import javax.inject.Inject

internal class GenreRepository @Inject constructor(
    private val dao: GenreDAO,
    private val service: ItunesService,
    private val resources: Resources,
    private val preferences: Preferences,
    private val timeProvider: TimeProvider,
    @GlobalCompositeDisposable private val globalDisposable: CompositeDisposable
) : Repository<Genre> {

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

    // TODO extract to a class
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
