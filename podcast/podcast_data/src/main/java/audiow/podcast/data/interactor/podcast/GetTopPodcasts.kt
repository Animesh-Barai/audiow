package audiow.podcast.data.interactor.podcast

import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import audiow.core.interactor.Interactor
import audiow.core.interactor.invoke
import audiow.podcast.data.interactor.user.GetPreferredCountry
import audiow.podcast.data.model.Podcast
import audiow.podcast.data.repository.genre.GenreRepository
import audiow.podcast.service.itunes.ItunesService
import toDomain
import javax.inject.Inject

interface GetTopPodcasts : Interactor<Int, Single<List<Podcast>>>

internal class GetTopPodcastsImpl @Inject constructor(
    private val itunesService: ItunesService,
    private val genreRepository: GenreRepository,
    private val getPreferredCountry: GetPreferredCountry
) : GetTopPodcasts {

    override fun invoke(limit: Int): Single<List<Podcast>> =
        Singles.zip(
            itunesService.getFeedByCountry(
                countryIso = getPreferredCountry(),
                limit = limit
            ),
            genreRepository.getAll()
        ) { podcasts, genres -> podcasts.map { it.toDomain(genres) } }

}