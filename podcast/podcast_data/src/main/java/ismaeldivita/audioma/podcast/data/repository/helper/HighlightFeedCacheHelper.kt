package ismaeldivita.audioma.podcast.data.repository.helper

import io.reactivex.Completable
import io.reactivex.Single
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.util.reactive.SchedulersProvider
import ismaeldivita.audioma.podcast.data.model.FeedSection
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.storage.database.dao.feed.FeedHighlightDAO
import ismaeldivita.audioma.podcast.data.storage.database.entity.feed.FeedHighlightEntity
import javax.inject.Inject

internal class HighlightFeedCacheHelper @Inject constructor(
    private val highlightDAO: FeedHighlightDAO,
    private val podcastRepository: Repository<Podcast>,
    private val schedulersProvider: SchedulersProvider
) : FeedCacheHelper {

    override fun getAll(): Single<List<Pair<Int, FeedSection>>> =
        highlightDAO.getAllHighlights()
            .flatMap<List<Pair<Int, FeedSection>>> { highlights ->
                podcastRepository.findByIds(highlights.map { it.id })
                    .map { podcasts ->
                        podcasts.map { podcast ->
                            val order = highlights.first { it.id == podcast.id }.order
                            order to FeedSection.Highlight(podcast)
                        }
                    }
            }.subscribeOn(schedulersProvider.io())

    override fun addAll(elements: List<FeedSection>): Completable {
        val highlights = elements.mapIndexed { index, section -> index to section }
            .filter { (_, highlight) -> highlight is FeedSection.Highlight }
            .map { (order, highlight) -> order to highlight as FeedSection.Highlight }

        return podcastRepository.addAll(highlights.map { it.second.podcast })
            .andThen(highlightDAO.insertAll(highlights.map { (order, highlight) ->
                FeedHighlightEntity(id = highlight.podcast.id, order = order)
            })).subscribeOn(schedulersProvider.io())
    }

    override fun delete() = highlightDAO.deleteAll().subscribeOn(schedulersProvider.io())

}