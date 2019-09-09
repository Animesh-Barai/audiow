package ismaeldivita.audioma.podcast.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.util.reactive.SchedulersProvider
import ismaeldivita.audioma.podcast.data.model.Artwork
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.storage.database.dao.PodcastDAO
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastArtworkEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.PodcastWrapperEntity
import javax.inject.Inject

internal class PodcastRepository @Inject constructor(
    private val dao: PodcastDAO,
    private val genreRepository: Repository<Genre>,
    private val schedulers: SchedulersProvider
) : Repository<Podcast> {

    override fun add(element: Podcast): Completable {
        val podcastWrapper = PodcastWrapperEntity(
            podcast = element.toEntity(),
            artworkList = element.toArtworkEntityList(),
            genreIds = element.genreList.map { it.id }
        )
        return Completable.fromCallable { dao.podcastWrapperTransaction(podcastWrapper) }
            .subscribeOn(schedulers.io())
    }

    override fun addAll(elements: List<Podcast>): Completable =
        throw UnsupportedOperationException()

    override fun remove(element: Podcast) = dao.delete(element.toEntity())
        .subscribeOn(schedulers.io())

    override fun findById(id: Any) = genreRepository.getAll()
        .flatMapMaybe { genreList -> dao.findById(id as Int).map { it.toDomain(genreList) } }
        .subscribeOn(schedulers.io())

    override fun getAll(): Single<List<Podcast>> = genreRepository.getAll()
        .flatMap { genreList ->
            dao.getAll().map { podcasts -> podcasts.map { it.toDomain(genreList) } }
        }.subscribeOn(schedulers.io())

    override fun clear(): Completable = dao.deleteAll()

    private fun PodcastWrapperEntity.toDomain(genreList: List<Genre>) = Podcast(
        id = podcast.id,
        title = podcast.title,
        artistName = podcast.artistName,
        rssUrl = podcast.rssUrl,
        artworkList = artworkList.map { Artwork(it.url, it.width, it.height) },
        primaryGenre = genreList.first { it.id == podcast.primaryGenre },
        genreList = genreList.filter { genreIds.contains(it.id) },
        explicit = podcast.explicit
    )

    private fun Podcast.toEntity() = PodcastEntity(
        id = id,
        title = title,
        artistName = artistName,
        primaryGenre = primaryGenre.id,
        rssUrl = rssUrl,
        explicit = explicit
    )

    private fun Podcast.toArtworkEntityList() = artworkList.map {
        PodcastArtworkEntity(
            url = it.url,
            width = it.width,
            height = it.height,
            podcastIdFk = id
        )
    }

}