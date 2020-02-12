package audiow.podcast.data.repository.discover

import io.reactivex.Completable
import io.reactivex.Single
import audiow.core.data.repository.Repository
import audiow.podcast.data.model.Discover
import audiow.podcast.data.model.Podcast
import audiow.podcast.data.storage.database.dao.discover.DiscoverHighlightDAO
import audiow.podcast.data.storage.database.entity.discover.DiscoverHighlightEntity
import javax.inject.Inject

internal class HighlightDiscoverCacheHelper @Inject constructor(
    private val highlightDAO: DiscoverHighlightDAO,
    private val podcastRepository: Repository<Podcast>
) : DiscoverCacheHelper {

    override fun getAll(): Single<List<Pair<Int, Discover>>> =
        highlightDAO.getAllHighlights()
            .flatMap<List<Pair<Int, Discover>>> { highlights ->
                podcastRepository.findByIds(highlights.map { it.id })
                    .map { podcasts ->
                        podcasts.map { podcast ->
                            val order = highlights.first { it.id == podcast.id }.order
                            order to Discover.Highlight(podcast)
                        }
                    }
            }

    override fun addAll(elements: List<Discover>): Completable {
        val highlights = elements.mapIndexed { index, section -> index to section }
            .filter { (_, highlight) -> highlight is Discover.Highlight }
            .map { (order, highlight) -> order to highlight as Discover.Highlight }

        return podcastRepository.addAll(highlights.map { it.second.podcast })
            .andThen(highlightDAO.insertAll(highlights.map { (order, highlight) ->
                DiscoverHighlightEntity(id = highlight.podcast.id, order = order)
            }))
    }

    override fun delete() = highlightDAO.deleteAll()

}