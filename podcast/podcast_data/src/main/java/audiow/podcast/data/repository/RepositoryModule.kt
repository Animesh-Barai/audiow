package audiow.podcast.data.repository

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import audiow.core.data.repository.Repository
import audiow.core.data.repository.RepositoryWatcher
import audiow.podcast.data.model.*
import audiow.podcast.data.repository.discover.DiscoverRepository
import audiow.podcast.data.repository.discover.BannerDiscoverCacheHelper
import audiow.podcast.data.repository.discover.DiscoverCacheHelper
import audiow.podcast.data.repository.discover.GenreCacheHelper
import audiow.podcast.data.repository.discover.HighlightDiscoverCacheHelper
import audiow.podcast.data.repository.feed.EpisodeRepository
import audiow.podcast.data.repository.feed.FeedRepository
import audiow.podcast.data.repository.genre.GenreRepository
import audiow.podcast.data.repository.podcast.PodcastRepository
import dagger.Reusable
import javax.inject.Singleton

@Module
internal abstract class RepositoryModule {

    @Binds
    @Reusable
    internal abstract fun bindGenreRepository(p: GenreRepository): Repository<Genre>

    @Binds
    @Reusable
    internal abstract fun bindPodcastRepository(p: PodcastRepository): Repository<Podcast>

    @Binds
    @Reusable
    internal abstract fun bindDiscoverRepository(p: DiscoverRepository): Repository<Discover>

    @Binds
    @Reusable
    internal abstract fun bindFeedRepository(p: FeedRepository): Repository<Feed>

    @Binds
    @Reusable
    internal abstract fun bindEpisodeRepository(p: EpisodeRepository): Repository<Episode>

    @Binds
    @Reusable
    internal abstract fun bindFeedRepositoryWatcher(p: FeedRepository): RepositoryWatcher<Feed>

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