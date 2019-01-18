package ismaeldivita.podkast.core

import dagger.Module
import ismaeldivita.podkast.core.monitoring.MonitoringModule

@Module(
    includes = [
        MonitoringModule::class
    ]
)
interface CoreModule