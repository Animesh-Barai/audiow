package ismaeldivita.podkast.data.repository

import dagger.Binds
import dagger.Module
import ismaeldivita.podkast.service.model.Genre
import ismaeldivita.podkast.service.model.Podcast
import javax.inject.Singleton

@Module
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    internal abstract fun bindGenreRepository(genreRepository: GenreRepository): Repository<Genre>

    @Binds
    @Singleton
    internal abstract fun bindPodcastRepository(podcastRepository: PodcastRepository): Repository<Podcast>

}