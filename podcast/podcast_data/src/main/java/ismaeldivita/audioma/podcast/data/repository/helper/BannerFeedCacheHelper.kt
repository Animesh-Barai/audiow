package ismaeldivita.audioma.podcast.data.repository.helper

import io.reactivex.Completable
import io.reactivex.Single
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.core.util.reactive.SchedulersProvider
import ismaeldivita.audioma.podcast.data.model.FeedSection
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.storage.database.dao.feed.FeedBannerDAO
import javax.inject.Inject

internal class BannerFeedCacheHelper @Inject constructor(
    private val bannerDAO: FeedBannerDAO,
    private val podcastRepository: Repository<Podcast>
) : FeedCacheHelper {

    override fun getAll(): Single<List<Pair<Int, FeedSection>>> =
        bannerDAO.getAll()
            .flatMap { banners ->
                podcastRepository.findByIds(banners.map { banner ->
                    banner.podcasts.map { it.podcastId }
                }).map { podcasts ->
                    banners.map { banner ->
                        val bannerPodcasts = podcasts.filter {
                            banner.podcasts.map { podcast -> podcast.podcastId }.contains(it.id)
                        }
                        banner.banner.order to FeedSection.Banner(bannerPodcasts)
                    }
                }
            }

    override fun addAll(elements: List<FeedSection>): Completable {
        val banners = elements.mapIndexed { index, section -> index to section }
            .filter { (_, banner) -> banner is FeedSection.Banner }
            .map { (order, banner) -> order to banner as FeedSection.Banner }

        return podcastRepository.addAll(banners.map { it.second.podcasts }.flatten())
            .andThen(Completable.fromCallable {
                bannerDAO.insert(banners.associate { (order, banner) ->
                    order to banner.podcasts.map { it.id }
                })
            })
    }

    override fun delete() = bannerDAO.deleteAll()
}