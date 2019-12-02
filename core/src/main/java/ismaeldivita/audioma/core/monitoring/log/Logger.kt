package ismaeldivita.audioma.core.monitoring.log

import ismaeldivita.audioma.core.monitoring.log.Logger.Level.*
import javax.inject.Provider

object Logger {

    private val handlers = arrayListOf<Handler>()
    private lateinit var formatter: Formatter
    private lateinit var metadataProvider: Provider<Metadata>

    internal fun initialize(
        metadataProvider: Provider<Metadata>,
        formatter: Formatter,
        handlers: List<Handler>
    ) {
        this.metadataProvider = metadataProvider
        this.formatter = formatter
        this.handlers.addAll(handlers)
    }

    fun d(message: String, properties: Map<String, Any?> = emptyMap()) =
        log(message, properties, DEBUG)

    fun i(message: String, properties: Map<String, Any?> = emptyMap()) =
        log(message, properties, INFO)


    fun w(message: String, properties: Map<String, Any?> = emptyMap()) =
        log(message, properties, WARN)


    fun e(message: String, properties: Map<String, Any?> = emptyMap()) =
        log(message, properties, ERROR)

    private fun log(message: String, properties: Map<String, Any?>, level: Level) {
        val formattedMessage = formatter.format(
            message,
            properties,
            level,
            metadataProvider.get()
        )
        handlers.forEach { it.log(formattedMessage, level) }
    }

    internal interface Handler {
        fun log(message: String, level: Level)
    }

    internal interface Formatter {
        fun format(
            message: String,
            properties: Map<String, Any?>,
            level: Level,
            metadata: Metadata
        ): String
    }

    internal enum class Level(val value: Int) {
        DEBUG(0),
        INFO(1),
        WARN(2),
        ERROR(3)
    }
}








