package audiow.podcast.service.itunes.parser.json

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import audiow.podcast.service.itunes.parser.json.typeadapter.GenreTreeTypeAdapter
import audiow.podcast.service.itunes.parser.json.typeadapter.PodcastJsonTypeAdapter
import audiow.podcast.service.itunes.parser.json.typeadapter.PodcastTypeAdapter
import audiow.podcast.service.itunes.parser.json.typeadapter.SearchJsonTypeAdater
import java.util.*

internal object MoshiProvider {

    val instanceWithAdapters: Moshi by lazy {
        Moshi.Builder()
                .add(GenreTreeTypeAdapter)
                .add(PodcastJsonTypeAdapter)
                .add(SearchJsonTypeAdater)
                .add(PodcastTypeAdapter)
                .add(Date::class.java, Rfc3339DateJsonAdapter())
                .add(KotlinJsonAdapterFactory())
                .build()
    }

    val instance: Moshi by lazy {
        Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
    }

}
