package ismaeldivita.audioma.podcast.data.interactor

import dagger.Binds
import dagger.Module
import ismaeldivita.audioma.podcast.data.interactor.feed.GetFeed
import ismaeldivita.audioma.podcast.data.interactor.feed.GetFeedImpl
import ismaeldivita.audioma.podcast.data.interactor.feed.GetPreferredFeedGenreSections
import ismaeldivita.audioma.podcast.data.interactor.feed.GetPreferredFeedGenreSectionsImpl
import ismaeldivita.audioma.podcast.data.interactor.genre.GetGenreTree
import ismaeldivita.audioma.podcast.data.interactor.genre.GetGenreTreeImpl
import ismaeldivita.audioma.podcast.data.interactor.user.GetPreferredCountry
import ismaeldivita.audioma.podcast.data.interactor.user.GetPreferredCountryImpl
import ismaeldivita.audioma.podcast.data.interactor.user.GetPreferredGenres
import ismaeldivita.audioma.podcast.data.interactor.user.GetPreferredGenresImpl

@Module
internal interface InteractorModule {

    @Binds
    fun bindGetFeed(p: GetFeedImpl): GetFeed

    @Binds
    fun bindFeedGenreSections(p: GetPreferredFeedGenreSectionsImpl): GetPreferredFeedGenreSections

    @Binds
    fun bindGetGenreTree(p: GetGenreTreeImpl): GetGenreTree

    @Binds
    fun bindGetPrefferedGenres(p: GetPreferredGenresImpl): GetPreferredGenres

    @Binds
    fun bindGetPreferredCountry(p: GetPreferredCountryImpl): GetPreferredCountry
}