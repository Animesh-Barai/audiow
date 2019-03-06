package ismaeldivita.podkast.service.model

import java.util.*

class GenreTree(val root: Genre) : Iterable<Genre> {

    companion object {
        const val ROOT_GENRE_ID = 26
    }

    constructor(list: List<Genre>) : this(list.first { it.id == ROOT_GENRE_ID })

    override fun iterator() = object : AbstractIterator<Genre>() {
        val nodes = buildList(root)

        fun buildList(node: Genre): LinkedList<Genre> =
                if (node.detail?.subgenres.orEmpty().isEmpty()) {
                    LinkedList(listOf(node))
                } else {
                    LinkedList(listOf(node) +
                            node.detail?.subgenres.orEmpty().map { buildList(it) }.flatten())
                }

        override fun computeNext() = if (nodes.isEmpty()) done() else setNext(nodes.removeFirst())

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GenreTree

        if (root != other.root) return false

        return true
    }

    override fun hashCode(): Int {
        return root.hashCode()
    }

    override fun toString(): String {
        return "GenreTree(root=$root)"
    }

}
