package ismaeldivita.audioma.podcast.data.repository

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import ismaeldivita.audioma.core.data.repository.Repository
import ismaeldivita.audioma.podcast.data.model.FeedSection
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.data.model.Podcast
import ismaeldivita.audioma.podcast.data.repository.helper.BannerFeedCacheHelper
import ismaeldivita.audioma.podcast.data.repository.helper.FeedCacheHelper
import ismaeldivita.audioma.podcast.data.repository.helper.GenreSectionsCacheHelper
import ismaeldivita.audioma.podcast.data.repository.helper.HighlightFeedCacheHelper
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
    internal abstract fun bindFeedRepository(p: FeedRepository): Repository<FeedSection>

    @Binds
    @IntoSet
    internal abstract fun bindBannerFeedCacheHelper(p: BannerFeedCacheHelper): FeedCacheHelper

    @Binds
    @IntoSet
    internal abstract fun bindHighlightFeedCacheHelper(p: HighlightFeedCacheHelper): FeedCacheHelper

    @Binds
    @IntoSet
    internal abstract fun bindGenreFeedCacheHelper(p: GenreSectionsCacheHelper): FeedCacheHelper
}