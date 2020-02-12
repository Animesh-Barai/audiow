package audiow.core.util

import dagger.Module
import audiow.core.util.reactive.ReactiveModule
import audiow.core.util.time.TimeModule

@Module(includes = [TimeModule::class, ReactiveModule::class])
internal class UtilModule