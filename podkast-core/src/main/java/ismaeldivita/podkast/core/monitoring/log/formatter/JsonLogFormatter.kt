package ismaeldivita.podkast.core.monitoring.log.formatter

import com.squareup.moshi.Moshi
import ismaeldivita.podkast.core.monitoring.log.Logger
import ismaeldivita.podkast.core.monitoring.log.Logger.Level
import ismaeldivita.podkast.core.monitoring.log.Metadata
import javax.inject.Inject

internal class JsonLogFormatter @Inject constructor() : Logger.Formatter {

    private val jsonAdapter by lazy {
        Moshi.Builder().build().adapter(Any::class.java)
    }

    override fun format(
        message: String,
        properties: Map<String, Any>,
        level: Level,
        metadata: Metadata
    ): String = mapOf(
        "message" to message,
        "level" to level.name,
        "properties" to properties,
        "metadata" to parseMetadataToObject(metadata)
    ).let(jsonAdapter::toJson)


    private fun parseMetadataToObject(metadata: Metadata): Map<String, Any> = mapOf(
        "created" to metadata.created,
        "origin" to metadata.origin
    )

}