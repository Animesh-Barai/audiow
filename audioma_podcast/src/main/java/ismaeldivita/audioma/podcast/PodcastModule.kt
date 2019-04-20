package ismaeldivita.audioma.podcast

import dagger.Module
import ismaeldivita.audioma.podcast.data.DataModule

@Module(includes = [DataModule::class])
interface PodcastModule