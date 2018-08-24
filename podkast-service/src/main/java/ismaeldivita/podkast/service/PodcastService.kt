package ismaeldivita.podkast.service

import io.reactivex.Single
import ismaeldivita.podkast.service.model.Podcast
import ismaeldivita.podkast.service.parser.MoshiProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PodcastService {

    @GET("search?media=podcast&entity=podcast")
    fun search(
            @Query("term") searchTerm: String = "podcast",
            @Query("country") countryIso: String = "US",
            @Query("genreId") filterByGenreId: Int? = null,
            @Query("limit") limit: Int? = null
    ): Single<List<Podcast>>

    @GET("WebObjects/MZStoreServices.woa/ws/genres")
    fun getGenre(
            @Query("id") genreId: String = "26",
            @Query("cc") countryIso: String = "US"
    ): Single<Any>

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
                .addConverterFactory(MoshiConverterFactory.create(MoshiProvider.instance))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(PodcastService::class.java)
    }

}
