package audiow.podcast.data.interactor.podcast

import io.reactivex.Single
import audiow.core.interactor.Interactor
import audiow.core.interactor.invoke
import audiow.podcast.data.interactor.podcast.GetPreferredGenrePodcasts.Input
import audiow.podcast.data.interactor.user.GetPreferredCountry
import audiow.podcast.data.interactor.user.GetPreferredGenres
import audiow.podcast.data.model.Genre
import audiow.podcast.data.model.Podcast
import audiow.podcast.data.repository.genre.GenreRepository
import audiow.podcast.service.itunes.ItunesService
import toDomain
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