package ismaeldivita.podkast.data.repository

import dagger.Binds
import dagger.Module
import ismaeldivita.podkast.service.itunes.model.ItunesGenre
import ismaeldivita.podkast.service.itunes.model.ItunesPodcast
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