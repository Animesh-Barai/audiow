package ismaeldivita.noizu.podcast.data.repository

import dagger.Binds
import dagger.Module
import ismaeldivita.noizu.podcast.service.itunes.model.ItunesGenre
import ismaeldivita.noizu.podcast.service.itunes.model.ItunesPodcast
import javax.inject.Singleton

@Module
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    internal abstract fun bindGenreRepository(genreRepository: GenreRepository): Repository<ItunesGenre>

    @Binds
    @Singleton
    internal abstract fun bindPodcastRepository(podcastRepository: PodcastRepository): Repository<ItunesPodcast>

}