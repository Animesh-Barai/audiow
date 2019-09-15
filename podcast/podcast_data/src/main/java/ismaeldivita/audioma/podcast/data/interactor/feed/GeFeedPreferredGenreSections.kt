package ismaeldivita.audioma.podcast.data.interactor.feed

import io.reactivex.Single
import ismaeldivita.audioma.core.interactor.Interactor
import ismaeldivita.audioma.core.interactor.invoke
import ismaeldivita.audioma.podcast.data.interactor.user.GetPreferredCountry
import ismaeldivita.audioma.podcast.data.interactor.user.GetPreferredGenres
import ismaeldivita.audioma.podcast.data.model.Artwork
import ismaeldivita.audioma.podcast.data.model.FeedSection
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.repository.GenreRepository
import ismaeldivita.audioma.podcast.service.itunes.ItunesService
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesPodcast
import javax.inject.Inject

interface GeFeedPreferredGenreSections : Interactor<Unit, Single<List<FeedSection.GenreSection>>>

internal class GetPreferredFeedGenreSectionsImpl @Inject constructor(
    private val getPreferredGenres: GetPreferredGenres,
    private val getPreferredCountry: GetPreferredCountry,
    private val genreRepository: GenreRepository,
    private val itunesService: ItunesService
) : GeFeedPreferredGenreSections {

    override fun invoke(p: Unit): Single<List<FeedSection.GenreSection>> {
        val genreCache = genreRepository.getAll().cache()

        return getPreferredGenres()
            .flattenAsObservable { it }
            .concatMapEager { preferredGenre ->
                itunesService
                    .getFeedByGenre(
                        countryIso = getPreferredCountry(),
                        genreId = preferredGenre.id,
                        limit = 10
                    )
                    .toObservable()
                    .flatMap { podcasts -> genreCache.toObservable().map { podcasts to it } }
                    .map { (podcasts, genres) ->
                        FeedSection.GenreSection(
                            preferredGenre,
                            podcasts.map { it.toDomain(genres) })
                    }
            }
            .toList()
    }

    private fun ItunesPodcast.toDomain(genres: List<Genre>) = Podcast(
        id = id,
        title = title,
        artistName = artistName,
        rssUrl = rssUrl,
        artworkList = artworkList.map { Artwork(it.url, it.width, it.height) },
        primaryGenre = genres.first { it.id == primaryGenreId },
        genreList = genreListId.map { id -> genres.first { it.id == id } },
        explicit = explicit
    )
}