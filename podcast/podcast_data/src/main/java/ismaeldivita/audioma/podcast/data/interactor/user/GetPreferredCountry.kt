package ismaeldivita.audioma.podcast.data.interactor.user

import ismaeldivita.audioma.core.interactor.Interactor
import javax.inject.Inject

interface GetPreferredCountry : Interactor<Unit, String>

internal class GetPreferredCountryImpl @Inject constructor() : GetPreferredCountry {

    // TODO Store and fetch this from Firebase.Database or locally preference for guest users
    override fun invoke(p: Unit): String = "BR"

}