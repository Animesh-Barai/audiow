package ismaeldivita.audioma.podcast.data.initializer

import io.reactivex.Completable
import ismaeldivita.audioma.core.android.LaunchInitializer
import ismaeldivita.audioma.podcast.data.repository.GenreRepository
import javax.inject.Inject

internal class PodcastDataInitializer @Inject constructor(
    private val genreRepository: GenreRepository
) : LaunchInitializer {

    override fun initialize(): Completable = genreRepository.getAll().ignoreElement()

}