package ismaeldivita.noizu.core.monitoring

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import ismaeldivita.noizu.core.android.ApplicationInitializer
import ismaeldivita.noizu.core.monitoring.log.LogInitializer

@Module
internal abstract class MonitoringModule {

    @Binds
    @IntoSet
    internal abstract fun bindLogInitializer(initializer: LogInitializer): ApplicationInitializer

}