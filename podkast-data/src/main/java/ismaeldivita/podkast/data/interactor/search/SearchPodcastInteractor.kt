package ismaeldivita.podkast.data.interactor.search

import io.reactivex.Single
import ismaeldivita.podkast.service.itunes.model.ItunesPodcast

interface SearchPodcastInteractor {

    fun search(term: String): Single<List<ItunesPodcast>>
    
}