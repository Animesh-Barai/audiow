package audiow.podcast.data.interactor

import dagger.Binds
import dagger.Module
import audiow.podcast.data.interactor.discover.*
import audiow.podcast.data.interactor.discover.GetDiscoverImpl
import audiow.podcast.data.interactor.podcast.GetPreferredFeedGenrePodcastsImpl
import audiow.podcast.data.interactor.genre.GetGenreTree
import audiow.podcast.data.interactor.genre.GetGenreTreeImpl
import audiow.podcast.data.interactor.podcast.GetPreferredGenrePodcasts
import audiow.podcast.data.interactor.podcast.GetTopPodcasts
import audiow.podcast.data.interactor.podcast.GetTopPodcastsImpl
import audiow.podcast.data.interactor.user.GetPreferredCountry
import audiow.podcast.data.interactor.user.GetPreferredCountryImpl
import audiow.podcast.data.interactor.user.GetPreferredGenres
import audiow.podcast.data.interactor.user.GetPreferredGenresImpl

@Module
internal interface InteractorModule {

    @Binds
    fun bindGetFeed(p: GetDiscoverImpl): GetDiscover

    @Binds
    fun bindFeedGenreSections(p: GetPreferredFeedGenrePodcastsImpl): GetPreferredGenrePodcasts

    @Binds
    fun bindGetTopPodcasts(p: GetTopPodcastsImpl): GetTopPodcasts

    @Binds
    fun bindGetGenreTree(p: GetGenreTreeImpl): GetGenreTree

    @Binds
    fun bindGetPrefferedGenres(p: GetPreferredGenresImpl): GetPreferredGenres

    @Binds
    fun bindGetPreferredCountry(p: GetPreferredCountryImpl): GetPreferredCountry
}