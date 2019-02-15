package ismaeldivita.podkast.service

import io.reactivex.Single
import ismaeldivita.podkast.service.dto.GenreDTOTree
import ismaeldivita.podkast.service.dto.FeedDTO
import ismaeldivita.podkast.service.dto.PodcastDTO
import ismaeldivita.podkast.service.parser.ConverterRouterFactory
import ismaeldivita.podkast.service.parser.json.Json
import ismaeldivita.podkast.service.parser.xml.Xml
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface PodcastService {

    @GET("search?media=podcast&entity=podcast")
    @Json
    fun search(
            @Query("term") searchTerm: String = "podcast",
            @Query("country") countryIso: String = "US",
            @Query("genreId") filterByGenreId: Int? = null,
            @Query("limit") limit: Int? = null
    ): Single<List<PodcastDTO>>

    @GET("WebObjects/MZStoreServices.woa/ws/genres?id=26")
    @Json
    fun getGenreTree(
            @Query("cc") countryIso: String = "US"
    ): Single<GenreDTOTree>

    @GET
    @Xml
    @Headers("Accept: application/rss+xml, application/rdf+xml, application/atom+xml, application/xml, text/xml")
    fun getPodcast(@Url rssUrl: String): Single<FeedDTO>

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder(
            var baseUrl: String = "https://itunes.apple.com/",
            var client: OkHttpClient = OkHttpClient()
    ) {

        fun build(): PodcastService = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(ConverterRouterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(PodcastService::class.java)
    }

}
