package ismaeldivita.podkast.service.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi

internal inline fun <reified T> Moshi.adapter(): JsonAdapter<T> = this.adapter(T::class.java)

internal inline fun <reified T> Moshi.parse(json: String): T? = this.adapter<T>().fromJson(json)

internal inline fun <reified T> Moshi.parse(reader: JsonReader): T? = this.adapter<T>().fromJson(reader)


