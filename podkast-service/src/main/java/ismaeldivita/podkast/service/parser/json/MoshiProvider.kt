package ismaeldivita.podkast.service.parser.json

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ismaeldivita.podkast.service.parser.json.typeadapter.GenreTreeTypeAdapter
import ismaeldivita.podkast.service.parser.json.typeadapter.PodcastJsonTypeAdapter
import ismaeldivita.podkast.service.parser.json.typeadapter.PodcastTypeAdapter
import ismaeldivita.podkast.service.parser.json.typeadapter.SearchJsonTypeAdater

internal object MoshiProvider {

    val instanceWithAdapters: Moshi by lazy {
        Moshi.Builder()
                .add(GenreTreeTypeAdapter)
                .add(PodcastJsonTypeAdapter)
                .add(SearchJsonTypeAdater)
                .add(PodcastTypeAdapter)
                .add(KotlinJsonAdapterFactory())
                .build()
    }

    val instance: Moshi by lazy {
        Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
    }

}
