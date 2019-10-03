package ismaeldivita.audioma.podcast.data.interactor

import dagger.Binds
import dagger.Module
import ismaeldivita.audioma.podcast.data.interactor.feed.*
import ismaeldivita.audioma.podcast.data.interactor.feed.GetFeedImpl
import ismaeldivita.audioma.podcast.data.interactor.podcast.GetPreferredFeedGenrePodcastsImpl
import ismaeldivita.audioma.podcast.data.interactor.genre.GetGenreTree
import ismaeldivita.audioma.podcast.data.interactor.genre.GetGenreTreeImpl
import ismaeldivita.audioma.podcast.data.interactor.podcast.GetPreferredGenrePodcasts
import ismaeldivita.audioma.podcast.data.interactor.podcast.GetTopPodcasts
import ismaeldivita.audioma.podcast.data.interactor.podcast.GetTopPodcastsImpl
import ismaeldivita.audioma.podcast.data.interactor.user.GetPreferredCountry
import ismaeldivita.audioma.podcast.data.interactor.user.GetPreferredCountryImpl
import ismaeldivita.audioma.podcast.data.interactor.user.GetPreferredGenres
import ismaeldivita.audioma.podcast.data.interactor.user.GetPreferredGenresImpl

@Module
internal interface InteractorModule {

    @Binds
    fun bindGetFeed(p: GetFeedImpl): GetFeed

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