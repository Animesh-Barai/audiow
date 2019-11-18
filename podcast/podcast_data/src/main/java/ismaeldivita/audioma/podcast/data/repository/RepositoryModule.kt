package ismaeldivita.audioma.podcast.data.repository

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.podcast.data.model.DiscoverItem
import ismaeldivita.audioma.podcast.data.model.Feed
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.repository.discover.DiscoverRepository
import ismaeldivita.audioma.podcast.data.repository.discover.BannerDiscoverCacheHelper
import ismaeldivita.audioma.podcast.data.repository.discover.DiscoverCacheHelper
import ismaeldivita.audioma.podcast.data.repository.discover.GenreCacheHelper
import ismaeldivita.audioma.podcast.data.repository.discover.HighlightDiscoverCacheHelper
import ismaeldivita.audioma.podcast.data.repository.feed.FeedRepository
import ismaeldivita.audioma.podcast.data.repository.genre.GenreRepository
import ismaeldivita.audioma.podcast.data.repository.podcast.PodcastRepository
import javax.inject.Singleton

@Module
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    internal abstract fun bindGenreRepository(p: GenreRepository): Repository<Genre>

    @Binds
    @Singleton
    internal abstract fun bindPodcastRepository(p: PodcastRepository): Repository<Podcast>

    @Binds
    @Singleton
    internal abstract fun bindDiscoverRepository(p: DiscoverRepository): Repository<DiscoverItem>

    @Binds
    @Singleton
    internal abstract fun bindFeedRepository(p: FeedRepository): Repository<Feed>

    @Binds
    @IntoSet
    internal abstract fun bindBannerFeedCacheHelper(p: BannerDiscoverCacheHelper): DiscoverCacheHelper

    @Binds
    @IntoSet
    internal abstract fun bindHighlightFeedCacheHelper(p: HighlightDiscoverCacheHelper): DiscoverCacheHelper

    @Binds
    @IntoSet
    internal abstract fun bindGenreFeedCacheHelper(p: GenreCacheHelper): DiscoverCacheHelper
}