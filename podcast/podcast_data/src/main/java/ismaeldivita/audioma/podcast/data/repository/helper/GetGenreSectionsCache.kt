package ismaeldivita.audioma.podcast.data.repository.helper

import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import ismaeldivita.audioma.podcast.data.model.FeedSection
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.repository.GenreRepository
import ismaeldivita.audioma.podcast.data.repository.PodcastRepository
import ismaeldivita.audioma.podcast.data.storage.database.dao.FeedDAO
import ismaeldivita.audioma.podcast.data.storage.database.entity.FeedGenreSectionWrapperEntity
import javax.inject.Inject

internal class GetGenreSectionsCache @Inject constructor(
    private val feedDAO: FeedDAO,
    private val genreRepository: GenreRepository,
    private val podcastRepository: PodcastRepository
) : () -> Single<List<Pair<Int, FeedSection>>> {

    override fun invoke(): Single<List<Pair<Int, FeedSection>>> {
        return feedDAO.getAllGenreSections()
            .flatMap { mapToFeedSection(it) }
    }

    private fun mapToFeedSection(sections: List<FeedGenreSectionWrapperEntity>) = Singles.zip(
        getGenres(sections),
        getPodcasts(sections)
    ) { genres, podcasts ->
        sections.map { section ->
            section.section.order to FeedSection.GenreSection(
                genre = genres.first { it.id == section.section.genreId },
                podcasts = podcasts.filter { podcastDomain ->
                    section.podcasts.any { it.podcastId == podcastDomain.id }
                }
            )
        }
    }

    private fun getGenres(sections: List<FeedGenreSectionWrapperEntity>): Single<List<Genre>> {
        val genresIds = sections.map { it.section.genreId }
        return genreRepository.findByIds(genresIds)
    }

    private fun getPodcasts(sections: List<FeedGenreSectionWrapperEntity>): Single<List<Podcast>> {
        val podcastIds = sections.map { sections ->
            sections.podcasts.map { podcast -> podcast.podcastId }
        }.flatten()
        return podcastRepository.findByIds(podcastIds)
    }
}