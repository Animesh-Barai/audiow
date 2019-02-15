package ismaeldivita.podkast.data.repository

import dagger.Binds
import dagger.Module
import ismaeldivita.podkast.service.dto.GenreDTO
import ismaeldivita.podkast.service.dto.PodcastDTO
import javax.inject.Singleton

@Module
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    internal abstract fun bindGenreRepository(genreRepository: GenreRepository): Repository<GenreDTO>

    @Binds
    @Singleton
    internal abstract fun bindPodcastRepository(podcastRepository: PodcastRepository): Repository<PodcastDTO>

}