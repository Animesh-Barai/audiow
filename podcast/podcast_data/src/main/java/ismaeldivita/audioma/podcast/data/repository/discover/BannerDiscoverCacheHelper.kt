package ismaeldivita.audioma.podcast.data.repository.discover

import io.reactivex.Completable
import io.reactivex.Single
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.podcast.data.model.DiscoverItem
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.storage.database.dao.discover.DiscoverBannerDAO
import javax.inject.Inject

internal class BannerDiscoverCacheHelper @Inject constructor(
    private val bannerDAO: DiscoverBannerDAO,
    private val podcastRepository: Repository<Podcast>
) : DiscoverCacheHelper {

    override fun getAll(): Single<List<Pair<Int, DiscoverItem>>> =
        bannerDAO.getAll()
            .flatMap { banners ->
                podcastRepository.findByIds(
                    banners.map { banner -> banner.podcasts.map { it.podcastId } }.flatten()
                ).map { podcasts ->
                    banners.map { banner ->
                        val bannerPodcasts = podcasts.filter {
                            banner.podcasts.map { podcast -> podcast.podcastId }.contains(it.id)
                        }
                        banner.banner.order to DiscoverItem.Banner(bannerPodcasts)
                    }
                }
            }

    override fun addAll(elements: List<DiscoverItem>): Completable {
        val banners = elements.mapIndexed { index, section -> index to section }
            .filter { (_, banner) -> banner is DiscoverItem.Banner }
            .map { (order, banner) -> order to banner as DiscoverItem.Banner }

        return podcastRepository.addAll(banners.map { it.second.podcasts }.flatten())
            .andThen(Completable.fromCallable {
                bannerDAO.insert(banners.associate { (order, banner) ->
                    order to banner.podcasts.map { it.id }
                })
            })
    }

    override fun delete() = bannerDAO.deleteAll()
}