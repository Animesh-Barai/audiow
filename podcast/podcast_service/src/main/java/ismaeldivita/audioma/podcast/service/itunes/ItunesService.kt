package ismaeldivita.audioma.podcast.service.itunes

import io.reactivex.Single
import ismaeldivita.audioma.core.util.standart.Tree
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesGenre
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesPodcastFeed
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesPodcast
import ismaeldivita.audioma.podcast.service.itunes.parser.ConverterRouterFactory
import ismaeldivita.audioma.podcast.service.itunes.parser.json.Json
import ismaeldivita.audioma.podcast.service.itunes.parser.xml.Xml
import ismaeldivita.audioma.podcast.service.util.HeaderKey
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.*

interface ItunesService {

    @Json
    @GET("search?media=podcast&entity=podcast")
    fun search(
        @Query("term") searchTerm: String = "podcast",
        @Query("country") countryIso: String = "US",
        @Query("genreId") filterByGenreId: Long? = null,
        @Query("limit") limit: Int? = null
    ): Single<List<ItunesPodcast>>

    @Json
    @GET("search?media=podcast&entity=podcast&term=podcast")
    fun getFeedByGenre(
        @Query("country") countryIso: String = "US",
        @Query("genreId") genreId: Long,
        @Query("limit") limit: Int? = null
    ): Single<List<ItunesPodcast>>

    @Json
    @GET("search?media=podcast&entity=podcast&term=podcast&genreId=$ROOT_GENRE_ID")
    fun getFeedByCountry(
        @Query("country") countryIso: String = "US",
        @Query("limit") limit: Int? = null
    ): Single<List<ItunesPodcast>>

    @Json
    @GET("WebObjects/MZStoreServices.woa/ws/genres?id=26")
    fun getGenreTree(
        @Query("cc") countryIso: String = "US"
    ): Single<Tree<ItunesGenre>>

    @Xml
    @GET
    @Headers("Accept: application/rss+xml, application/rdf+xml, application/atom+xml, application/xml, text/xml")
    fun getPodcastRss(
        @Url rssUrl: String,
        @Header(HeaderKey.modifiedSince) ifModifiedSince: String? = null,
        @Header(HeaderKey.noneMatch) ifNoneMatch: String? = null
    ): Single<Response<ItunesPodcastFeed>>

    companion object {
        const val ROOT_GENRE_ID: Long = 26

        fun build(
            baseUrl: String = "https://itunes.apple.com/",
            client: OkHttpClient = OkHttpClient()
        ): ItunesService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(ConverterRouterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ItunesService::class.java)
    }

}
