package audiow.podcast.data.initializer

import io.reactivex.Completable
import audiow.core.common.LaunchInitializer
import audiow.podcast.data.repository.genre.GenreRepository
import javax.inject.Inject

internal class PodcastDataInitializer @Inject constructor(
    private val genreRepository: GenreRepository
) : LaunchInitializer {

    override fun initialize(): Completable = genreRepository.getAll().ignoreElement()

}