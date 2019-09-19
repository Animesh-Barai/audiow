package ismaeldivita.audioma.podcast.feature

import dagger.Module
import ismaeldivita.audioma.podcast.data.DataModule
import ismaeldivita.audioma.podcast.feature.ui.BindingModule

@Module(includes = [DataModule::class, BindingModule::class])
abstract class PodcastModule