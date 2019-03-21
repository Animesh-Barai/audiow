package ismaeldivita.noizu.podcast.data.interactor.search

import io.reactivex.Single
import ismaeldivita.noizu.podcast.service.itunes.model.ItunesPodcast

interface SearchPodcastInteractor {

    fun search(term: String): Single<List<ItunesPodcast>>
    
}