package audiow.core

import dagger.Module
import audiow.core.android.AndroidModule
import audiow.core.data.preferences.PreferencesModule
import audiow.core.monitoring.MonitoringModule
import audiow.core.util.UtilModule

@Module(
    includes = [
        AndroidModule::class,
        MonitoringModule::class,
        PreferencesModule::class,
        UtilModule::class
    ]
)
abstract class CoreModule