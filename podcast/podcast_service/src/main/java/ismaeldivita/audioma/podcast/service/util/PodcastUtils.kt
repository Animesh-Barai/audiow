package ismaeldivita.audioma.podcast.service.util

object PodcastUtils {

    fun sanitizeDescription(text: String): String {
        return text.lines().joinToString(separator = "<br/>") { it.trim() }
    }
}