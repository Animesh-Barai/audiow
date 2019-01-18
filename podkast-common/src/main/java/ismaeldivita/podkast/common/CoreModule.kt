package ismaeldivita.podkast.common

import dagger.Module
import ismaeldivita.podkast.common.monitoring.MonitoringModule

@Module(
    includes = [
        MonitoringModule::class
    ]
)
interface CoreModule