package ismaeldivita.audioma.podcast.data.repository.discover

import io.reactivex.Completable
import io.reactivex.Single
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.podcast.data.model.DiscoverItem
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.storage.database.dao.discover.DiscoverHighlightDAO
import ismaeldivita.audioma.podcast.data.storage.database.entity.discover.DiscoverHighlightEntity
import javax.inject.Inject

internal class HighlightDiscoverCacheHelper @Inject constructor(
    private val highlightDAO: DiscoverHighlightDAO,
    private val podcastRepository: Repository<Podcast>
) : DiscoverCacheHelper {

    override fun getAll(): Single<List<Pair<Int, DiscoverItem>>> =
        highlightDAO.getAllHighlights()
            .flatMap<List<Pair<Int, DiscoverItem>>> { highlights ->
                podcastRepository.findByIds(highlights.map { it.id })
                    .map { podcasts ->
                        podcasts.map { podcast ->
                            val order = highlights.first { it.id == podcast.id }.order
                            order to DiscoverItem.Highlight(podcast)
                        }
                    }
            }

    override fun addAll(elements: List<DiscoverItem>): Completable {
        val highlights = elements.mapIndexed { index, section -> index to section }
            .filter { (_, highlight) -> highlight is DiscoverItem.Highlight }
            .map { (order, highlight) -> order to highlight as DiscoverItem.Highlight }

        return podcastRepository.addAll(highlights.map { it.second.podcast })
            .andThen(highlightDAO.insertAll(highlights.map { (order, highlight) ->
                DiscoverHighlightEntity(id = highlight.podcast.id, order = order)
            }))
    }

    override fun delete() = highlightDAO.deleteAll()

}