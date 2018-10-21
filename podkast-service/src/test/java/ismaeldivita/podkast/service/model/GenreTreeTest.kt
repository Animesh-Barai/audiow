package ismaeldivita.podkast.service.model

import org.junit.Test

class GenreTreeTest {

    @Test
    fun testzz() {
        val node22 = GenreNode(Genre(0, "2-2"), listOf(), "")
        val node21 = GenreNode(Genre(0, "2-1"), listOf(), "")
        val node11 = GenreNode(Genre(0, "1-1"), listOf(), "")
        val node12 = GenreNode(Genre(0, "1-2"), listOf(), "")

        val node2 = GenreNode(Genre(0, "2"), listOf(node21, node22), "")
        val node1 = GenreNode(Genre(0, "1"), listOf(node11, node12), "")

        val root = GenreNode(Genre(0, "0"), listOf(node1, node2), "")

        val tree = GenreTree(root)

        print("###")

        tree.forEach { println(it.genre.name) }

    }

}
