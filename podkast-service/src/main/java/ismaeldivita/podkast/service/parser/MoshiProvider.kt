package ismaeldivita.podkast.service.parser

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ismaeldivita.podkast.service.parser.typeadapter.GenreTreeTypeAdapter
import ismaeldivita.podkast.service.parser.typeadapter.PodcastJsonTypeAdapter
import ismaeldivita.podkast.service.parser.typeadapter.PodcastTypeAdapter
import ismaeldivita.podkast.service.parser.typeadapter.SearchJsonTypeAdater

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
