package ismaeldivita.podkast.data.repository

import dagger.Binds
import dagger.Module
import ismaeldivita.podkast.service.model.Genre
import javax.inject.Singleton

@Module
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    internal abstract fun bindGenreRepository(genreRepository: GenreRepository): Repository<Genre>
}