package ismaeldivita.noizu.core.monitoring.log.formatter

import ismaeldivita.noizu.core.monitoring.log.Logger
import ismaeldivita.noizu.core.monitoring.log.Metadata
import javax.inject.Inject

internal class SingleLineLogFormatter @Inject constructor() : Logger.Formatter {

    override fun format(
        message: String,
        properties: Map<String, Any>,
        level: Logger.Level,
        metadata: Metadata
    ): String = "${metadata.origin} -> $message"

}