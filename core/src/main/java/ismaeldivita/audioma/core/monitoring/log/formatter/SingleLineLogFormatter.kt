package ismaeldivita.audioma.core.monitoring.log.formatter

import ismaeldivita.audioma.core.monitoring.log.Logger
import ismaeldivita.audioma.core.monitoring.log.Metadata
import javax.inject.Inject

internal class SingleLineLogFormatter @Inject constructor() : Logger.Formatter {

    override fun format(
        message: String,
        properties: Map<String, Any?>,
        level: Logger.Level,
        metadata: Metadata
    ): String = "${metadata.origin} $message - $properties"

}