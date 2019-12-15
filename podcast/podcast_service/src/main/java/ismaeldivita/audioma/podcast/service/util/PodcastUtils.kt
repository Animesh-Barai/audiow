package ismaeldivita.audioma.podcast.service.util

import org.apache.commons.text.StringEscapeUtils

object PodcastUtils {

    fun sanitizeDescription(text: String): String = text
        .lines()
        .joinToString(separator = "<br/>") { it.trim() }
        .let(StringEscapeUtils::unescapeHtml4)
        .replace("<![CDATA[", "")
        .replace("]]>", "")
}