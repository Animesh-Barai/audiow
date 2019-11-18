package ismaeldivita.audioma.podcast.data.repository.discover

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.podcast.data.model.DiscoverItem
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.repository.genre.GenreRepository
import ismaeldivita.audioma.podcast.data.storage.database.dao.discover.DiscoverGenreSectionDAO
import ismaeldivita.audioma.podcast.data.storage.database.entity.discover.DiscoverGenreSectionEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.discover.DiscoverGenreSectionPodcastsEntity
import ismaeldivita.audioma.podcast.data.storage.database.entity.discover.FeedGenreSectionWrapperEntity
import javax.inject.Inject

internal class GenreCacheHelper @Inject constructor(
    private val discoverGenreSectionDAO: DiscoverGenreSectionDAO,
    private val genreRepository: GenreRepository,
    private val podcastRepository: Repository<Podcast>
) : DiscoverCacheHelper {

    override fun getAll(): Single<List<Pair<Int, DiscoverItem>>> =
        discoverGenreSectionDAO.getAllGenreSections()
            .flatMap { mapToFeedSection(it) }

    override fun addAll(elements: List<DiscoverItem>): Completable {
        val genreSections = elements.mapIndexed { index, section -> index to section }
            .filter { (_, section) -> section is DiscoverItem.GenreSection }
            .map { (order, section) -> order to section as DiscoverItem.GenreSection }

        val podcasts = genreSections.map { it.second.podcasts }.flatten()

        val entities = genreSections
            .associate { (order, section) ->
                val sectionEntity =
                    DiscoverGenreSectionEntity(
                        section.genre.id,
                        order
                    )
                val podcastSectionList = section.podcasts.map { podcast ->
                    DiscoverGenreSectionPodcastsEntity(
                        podcast.id,
                        sectionEntity.genreId
                    )
                }
                sectionEntity to podcastSectionList
            }

        return podcastRepository.addAll(podcasts)
            .andThen(Completable.fromCallable {
                discoverGenreSectionDAO.insertGenreSections(entities)
            })
    }

    override fun delete() = discoverGenreSectionDAO.deleteAll()

    private fun mapToFeedSection(sections: List<FeedGenreSectionWrapperEntity>) = Singles.zip(
        getGenres(sections),
        getPodcasts(sections)
    ) { genres, podcasts ->
        sections.map { section ->
            section.section.order to DiscoverItem.GenreSection(
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