package ismaeldivita.audioma.podcast.feature

import dagger.Module
import ismaeldivita.audioma.podcast.data.DataModule

@Module(includes = [DataModule::class])
interface PodcastModule