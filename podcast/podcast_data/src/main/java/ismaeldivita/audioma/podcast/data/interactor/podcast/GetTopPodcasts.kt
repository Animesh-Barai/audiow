package ismaeldivita.audioma.podcast.data.interactor.podcast

import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import ismaeldivita.audioma.core.interactor.Interactor
import ismaeldivita.audioma.core.interactor.invoke
import ismaeldivita.audioma.podcast.data.interactor.user.GetPreferredCountry
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.repository.GenreRepository
import ismaeldivita.audioma.podcast.data.util.toDomain
import ismaeldivita.audioma.podcast.service.itunes.ItunesService
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