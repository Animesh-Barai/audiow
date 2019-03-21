package ismaeldivita.audioma.podcast.service.itunes

import io.reactivex.Single
import ismaeldivita.audioma.core.util.standart.Tree
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesGenre
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesPodcastFeed
import ismaeldivita.audioma.podcast.service.itunes.model.ItunesPodcast
import ismaeldivita.audioma.podcast.service.itunes.parser.ConverterRouterFactory
import ismaeldivita.audioma.podcast.service.itunes.parser.json.Json
import ismaeldivita.audioma.podcast.service.itunes.parser.xml.Xml
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface ItunesService {

    @GET("search?media=podcast&entity=podcast")
    @Json
    fun search(
            @Query("term") searchTerm: String = "podcast",
            @Query("country") countryIso: String = "US",
            @Query("genreId") filterByGenreId: Int? = null,
            @Query("limit") limit: Int? = null
    ): Single<List<ItunesPodcast>>

    @GET("WebObjects/MZStoreServices.woa/ws/genres?id=26")
    @Json
    fun getGenreTree(
            @Query("cc") countryIso: String = "US"
    ): Single<Tree<ItunesGenre>>

    @GET
    @Xml
    @Headers("Accept: application/rss+xml, application/rdf+xml, application/atom+xml, application/xml, text/xml")
    fun getPodcast(@Url rssUrl: String): Single<ItunesPodcastFeed>

    companion object {

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
