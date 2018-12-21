package ismaeldivita.podkast.data.interactor.search

import io.reactivex.Single
import ismaeldivita.podkast.service.model.Podcast

interface SearchPodcastInteractor {

    fun search(term: String): Single<List<Podcast>>
    
}