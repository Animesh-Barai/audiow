package ismaeldivita.podkast.data.interactor.search

import io.reactivex.Single
import ismaeldivita.podkast.service.dto.PodcastDTO

interface SearchPodcastInteractor {

    fun search(term: String): Single<List<PodcastDTO>>
    
}