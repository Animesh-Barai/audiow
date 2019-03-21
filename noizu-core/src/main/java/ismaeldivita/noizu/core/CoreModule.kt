package ismaeldivita.noizu.core

import dagger.Module
import ismaeldivita.noizu.core.monitoring.MonitoringModule
import ismaeldivita.noizu.core.util.UtilModule

@Module(
    includes = [
        MonitoringModule::class,
        UtilModule::class
    ]
)
interface CoreModule