package ismaeldivita.podkast.core.monitoring

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import ismaeldivita.podkast.core.android.ApplicationInitializer
import ismaeldivita.podkast.core.monitoring.log.LogInitializer

@Module
internal abstract class MonitoringModule {

    @Binds
    @IntoSet
    internal abstract fun bindLogInitializer(initializer: LogInitializer): ApplicationInitializer

}