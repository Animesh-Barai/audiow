package ismaeldivita.audioma.core.util.time

import dagger.Module
import dagger.Provides
import dagger.Reusable
import java.util.*
import javax.inject.Singleton

@Module
class TimeModule {

    @Provides
    @Reusable
    internal fun timeProvider(timeProvider: CurrentTimeProvider): TimeProvider = timeProvider

    @Provides
    @Singleton
    internal fun locale(): Locale = Locale.getDefault()

}