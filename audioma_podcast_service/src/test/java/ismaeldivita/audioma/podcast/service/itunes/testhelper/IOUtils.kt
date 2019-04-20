package ismaeldivita.audioma.podcast.service.itunes.testhelper

import okio.BufferedSource
import okio.Okio

object IOUtils {

    fun fileToString(path: String): String =
            javaClass.getResourceAsStream(path)!!
                    .bufferedReader()
                    .use { it.readText() }

    fun fileToBufferedSource(path: String): BufferedSource =
            Okio.source(javaClass.getResourceAsStream(path)!!)
                    .let { Okio.buffer(it) }
}

