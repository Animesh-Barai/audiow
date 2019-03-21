package ismaeldivita.noizu.core.util.time

import org.threeten.bp.Clock
import javax.inject.Inject

internal class CurrentTimeProvider @Inject constructor() : TimeProvider {

    override fun getCurrentTimeMillis(): Long = Clock.systemUTC().millis()

}