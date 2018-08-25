package ismaeldivita.podkast.service.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

internal inline fun <reified T> Moshi.adapter(): JsonAdapter<T> = this.adapter(T::class.java)
