package ismaeldivita.audioma.core

import dagger.Module
import ismaeldivita.audioma.core.android.AndroidModule
import ismaeldivita.audioma.core.data.preferences.PreferencesModule
import ismaeldivita.audioma.core.monitoring.MonitoringModule
import ismaeldivita.audioma.core.util.UtilModule
import ismaeldivita.audioma.core.util.reactive.ReactiveModule

@Module(
    includes = [
        AndroidModule::class,
        MonitoringModule::class,
        PreferencesModule::class,
        UtilModule::class
    ]
)
abstract class CoreModule