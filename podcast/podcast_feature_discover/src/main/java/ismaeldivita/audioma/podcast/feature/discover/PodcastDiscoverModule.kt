package ismaeldivita.audioma.podcast.feature.discover

import dagger.Module
import ismaeldivita.audioma.podcast.data.DataModule
import ismaeldivita.audioma.podcast.feature.discover.ui.BindingModule

@Module(includes = [DataModule::class, BindingModule::class])
abstract class PodcastDiscoverModule