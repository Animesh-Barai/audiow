package ismaeldivita.podkast.common.monitoring

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import ismaeldivita.podkast.common.android.ApplicationInitializer
import ismaeldivita.podkast.common.monitoring.log.LoggerInitializer

@Module
abstract class MonitoringModule {

    @Binds
    @IntoSet
    internal abstract fun bindLogInitializer(initializer: LoggerInitializer): ApplicationInitializer

}