package ismaeldivita.audioma.core.monitoring

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import ismaeldivita.audioma.core.android.ApplicationInitializer
import ismaeldivita.audioma.core.monitoring.log.LogInitializer

@Module
internal abstract class MonitoringModule {

    @Binds
    @IntoSet
    internal abstract fun bindLogInitializer(initializer: LogInitializer): ApplicationInitializer

}