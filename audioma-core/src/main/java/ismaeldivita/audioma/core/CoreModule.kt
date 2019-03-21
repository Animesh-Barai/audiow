package ismaeldivita.audioma.core

import dagger.Module
import ismaeldivita.audioma.core.monitoring.MonitoringModule
import ismaeldivita.audioma.core.util.UtilModule

@Module(
    includes = [
        MonitoringModule::class,
        UtilModule::class
    ]
)
interface CoreModule