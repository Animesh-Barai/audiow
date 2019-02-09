package ismaeldivita.podkast.core

import dagger.Module
import ismaeldivita.podkast.core.monitoring.MonitoringModule
import ismaeldivita.podkast.core.util.UtilModule

@Module(
    includes = [
        MonitoringModule::class,
        UtilModule::class
    ]
)
interface CoreModule