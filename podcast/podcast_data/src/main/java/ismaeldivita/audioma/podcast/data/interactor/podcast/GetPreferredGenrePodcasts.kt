package ismaeldivita.audioma.podcast.data.interactor.podcast

import io.reactivex.Single
import ismaeldivita.audioma.core.interactor.Interactor
import ismaeldivita.audioma.core.interactor.invoke
import ismaeldivita.audioma.podcast.data.interactor.podcast.GetPreferredGenrePodcasts.*
import ismaeldivita.audioma.podcast.data.interactor.user.GetPreferredCountry
import ismaeldivita.audioma.podcast.data.interactor.user.GetPreferredGenres
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.repository.GenreRepository
import ismaeldivita.audioma.podcast.data.util.toDomain
import ismaeldivita.audioma.podcast.service.itunes.ItunesService
import javax.inject.Inject

interface GetPreferredGenrePodcasts : Interactor<Input, Single<Map<Genre, List<Podcast>>>> {
    data class Input(val count: Int, val limit: Int)
}

internal class GetPreferredFeedGenrePodcastsImpl @Inject constructor(
    private val getPreferredGenres: GetPreferredGenres,
    private val getPreferredCountry: GetPreferredCountry,
    private val genreRepository: GenreRepository,
    private val itunesService: ItunesService
) : GetPreferredGenrePodcasts {

    override fun invoke(input: Input): Single<Map<Genre, List<Podcast>>> =
        getPreferredGenres()
            .flattenAsObservable { it.take(input.count) }
            .concatMapEager { preferredGenre ->
                itunesService
                    .getFeedByGenre(
                        countryIso = getPreferredCountry(),
                        genreId = preferredGenre.id,
                        limit = input.limit
                    )
                    .toObservable()
                    .flatMap { podcasts ->
                        genreRepository.getAll()
                            .toObservable()
                            .map { podcasts to it }
                    }
                    .map { (podcasts, genres) ->
                        preferredGenre to podcasts.map { it.toDomain(genres) }
                    }
            }
            .toList()
            .map { it.associate { (genre, podcasts) -> genre to podcasts } }

}