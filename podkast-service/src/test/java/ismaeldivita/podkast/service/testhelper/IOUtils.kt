package ismaeldivita.podkast.service.testhelper

object IOUtils {

    fun fileToString(path: String): String =
            javaClass.getResourceAsStream(path)
                    .bufferedReader()
                    .use { it.readText() }

}

