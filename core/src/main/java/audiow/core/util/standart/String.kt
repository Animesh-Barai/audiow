package audiow.core.util.standart

import java.security.MessageDigest

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

fun String.sha1(): String {
    val hexChars = "0123456789ABCDEF"
    val bytes = MessageDigest
        .getInstance("SHA-1")
        .digest(toByteArray())
    val result = StringBuilder(bytes.size * 2)

    bytes.forEach {
        val i = it.toInt()
        result.append(hexChars[i shr 4 and 0x0f])
        result.append(hexChars[i and 0x0f])
    }
    return result.toString()
}
