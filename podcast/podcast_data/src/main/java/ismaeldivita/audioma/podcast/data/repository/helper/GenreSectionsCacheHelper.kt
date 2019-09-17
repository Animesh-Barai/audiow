package ismaeldivita.audioma.podcast.data.repository.helper

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.util.reactive.SchedulersProvider
import ismaeldivita.audioma.podcast.data.model.FeedSection
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.repository.GenreRepository
import ismaeldivita.audioma.podcast.data.storage.database.dao.feed.FeedGenreSectionDAO
import ismaeldivita.audioma.podcast.data.storage.database.entity.feed.FeedGenreSectionEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.feed.FeedGenreSectionPodcastsEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.feed.FeedGenreSectionWrapperEntity
import javax.inject.Inject

internal class GenreSectionsCacheHelper @Inject constructor(
    private val feedGenreSectionDAO: FeedGenreSectionDAO,
    private val genreRepository: GenreRepository,
    private val podcastRepository: Repository<Podcast>,
    private val schedulersProvider: SchedulersProvider
) : FeedCacheHelper {

    override fun getAll(): Single<List<Pair<Int, FeedSection>>> =
        feedGenreSectionDAO.getAllGenreSections()
            .flatMap<List<Pair<Int, FeedSection>>> {
                mapToFeedSection(it)
            }.subscribeOn(schedulersProvider.io())

    override fun addAll(elements: List<FeedSection>): Completable {
        val genreSections = elements.mapIndexed { index, section -> index to section }
            .filter { (_, section) -> section is FeedSection.GenreSection }
            .map { (order, section) -> order to section as FeedSection.GenreSection }

        val podcasts = genreSections.map { it.second.podcasts }.flatten()

        val entities = genreSections
            .associate { (order, section) ->
                val sectionEntity =
                    FeedGenreSectionEntity(
                        section.genre.id,
                        order
                    )
                val podcastSectionList = section.podcasts.map { podcast ->
                    FeedGenreSectionPodcastsEntity(
                        podcast.id,
                        sectionEntity.genreId
                    )
                }
                sectionEntity to podcastSectionList
            }

        return podcastRepository.addAll(podcasts)
            .andThen(Completable.fromCallable { feedGenreSectionDAO.insertGenreSections(entities) })
            .subscribeOn(schedulersProvider.io())
    }

    override fun delete() = feedGenreSectionDAO.deleteAll().subscribeOn(schedulersProvider.io())

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
        val podcastIds = sections.map { section ->
            section.podcasts.map { podcast -> podcast.podcastId }
        }.flatten()
        return podcastRepository.findByIds(podcastIds)
    }
}