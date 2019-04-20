package ismaeldivita.audioma.podcast.data.repository

import dagger.Binds
import dagger.Module
import ismaeldivita.audioma.core.data.Repository
import ismaeldivita.audioma.podcast.data.model.Genre
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesPodcast
import javax.inject.Singleton

@Module
internal abstract class PodcastRepositoryModule {

    @Binds
    @Singleton
    internal abstract fun bindGenreRepository(genreRepository: GenreRepository): Repository<Genre>

    @Binds
    @Singleton
    internal abstract fun bindPodcastRepository(podcastRepository: PodcastRepository): Repository<ItunesPodcast>

}