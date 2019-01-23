package ismaeldivita.podkast.core.util.time

import javax.inject.Inject

internal class CurrentTimeProvider @Inject constructor() : TimeProvider {

    override fun getCurrentTimeMillis(): Long = System.currentTimeMillis()

}