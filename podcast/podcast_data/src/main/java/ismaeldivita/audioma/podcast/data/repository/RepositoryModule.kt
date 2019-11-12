package ismaeldivita.audioma.podcast.data.repository

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.podcast.data.model.DiscoverItem
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.repository.helper.BannerDiscoverCacheHelper
import ismaeldivita.audioma.podcast.data.repository.helper.DiscoverCacheHelper
import ismaeldivita.audioma.podcast.data.repository.helper.GenreCacheHelper
import ismaeldivita.audioma.podcast.data.repository.helper.HighlightDiscoverCacheHelper
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
    internal abstract fun bindFeedRepository(p: DiscoverRepository): Repository<DiscoverItem>

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