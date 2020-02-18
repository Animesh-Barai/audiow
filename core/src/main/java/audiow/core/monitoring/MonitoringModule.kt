package audiow.core.monitoring

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import audiow.core.common.ApplicationInitializer
import audiow.core.monitoring.log.LogInitializer

@Module
internal abstract class MonitoringModule {

    @Binds
    @IntoSet
    internal abstract fun bindLogInitializer(initializer: LogInitializer): ApplicationInitializer

}