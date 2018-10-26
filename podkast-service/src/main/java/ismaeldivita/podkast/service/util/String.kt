package ismaeldivita.podkast.service.util

fun String.replaceLastOccurrence(target: String, replacement: String): String {
    val lastIndexOfTarget = lastIndexOf(target)
    return if (lastIndexOfTarget == -1) {
        this
    } else {
        StringBuilder(this)
                .replace(lastIndexOfTarget, lastIndexOfTarget + target.length, replacement)
                .toString()
    }
}
