package ismaeldivita.podkast.core.util

import dagger.Binds
import dagger.Module
import dagger.Reusable
import ismaeldivita.podkast.core.util.time.CurrentTimeProvider
import ismaeldivita.podkast.core.util.time.TimeProvider

@Module
abstract class UtilModule {

    @Binds
    @Reusable
    internal abstract fun bindTimeProvider(currentTimeProvider: CurrentTimeProvider): TimeProvider

}